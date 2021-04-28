<template>
  <div class="app-container">
    <el-button
      v-if="isShowReleaseBtn"
      type="primary"
      size="small"
      style="margin-bottom: 10px;"
      icon="el-icon-upload"
      @click="releaseDoc">
      发布文档
    </el-button>
    <el-tabs type="card">
      <el-tab-pane>
        <span slot="label"><i class="el-icon-document"/> 文档管理</span>
        <el-container>
          <el-aside style="min-height: 300px;width: 250px;">
            <el-button
              type="primary"
              plain
              size="mini"
              icon="el-icon-circle-plus-outline"
              @click.stop="addFolder"
            >
              添加目录
            </el-button>
            <el-input
              v-model="filterText"
              prefix-icon="el-icon-search"
              placeholder="搜索文档..."
              style="margin-bottom:10px;margin-top:10px;"
              size="mini"
              clearable
            />
            <el-tree
              ref="tree"
              :data="data"
              :props="defaultProps"
              :filter-node-method="filterNode"
              :highlight-current="true"
              :expand-on-click-node="false"
              empty-text="无数据"
              node-key="id"
            >
              <span slot-scope="{ node, data }" class="custom-tree-node" @click="() => onNodeClick(data)">
                <span v-if="data.label.length < 9">{{ data.label }}</span>
                <span v-else>
                  <el-tooltip :content="data.label" class="item" effect="light" placement="right">
                    <span>{{ data.label.substring(0, 9) + '...' }}</span>
                  </el-tooltip>
                </span>
                <span>
                  <el-button
                    v-if="!data.parentId"
                    type="text"
                    size="mini"
                    @click.stop="() => addNode(data)">
                    添加文档
                  </el-button>
                  <el-button
                    v-if="data.parentId||!data.children||data.children.length === 0"
                    type="text"
                    size="mini"
                    icon="el-icon-delete"
                    title="删除文档"
                    @click.stop="() => delNode(data)"/>
                </span>
              </span>
            </el-tree>
          </el-aside>
          <el-main v-show="docFormVisible" style="padding-top:0">
            <el-form ref="docForm" :inline="true" :model="docForm" :rules="rules" class="docFormCls" style="width: 100%">
              <el-form-item prop="name">
                <el-input v-model="docForm.name" class="doc-title" placeholder="文档名称" style="width: 600px;">
                  <template slot="prepend">{{ docForm.parentName }} --></template>
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-checkbox v-model="docForm.isShow" true-label="1" false-label="0">是否显示</el-checkbox>
              </el-form-item>
              <el-form-item label="插入模板" style="margin-bottom:0;display: block">
                <el-button v-for="item in templateList" :key="item.id" type="text" @click="insertApi(item)" >{{ item.name }}</el-button>
              </el-form-item>
              <mavon-editor
                v-model="docForm.content"
                :boxShadow="false"
                style="min-height: 500px"
                @change="onContentChange"
                @save="onContentSave"
              />
              <el-form-item>
                <el-button type="primary" style="margin-top: 10px;" @click="submitForm()">保存</el-button>
              </el-form-item>
            </el-form>
          </el-main>
          <!-- 添加目录dialog -->
          <el-dialog
            :visible.sync="dialogVisible"
            title="添加目录"
            width="35%"
            @close="closeAddFolderDlg">
            <el-form ref="addFolderForm" :model="addFolderForm" :rules="addFolderFormRules" label-width="120px">
              <el-form-item label="目录名称" prop="name">
                <el-input v-model="addFolderForm.name" placeholder="目录名称" style="width: 300px;" />
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="submitFolderForm('addFolderForm')">确 定</el-button>
              <el-button @click="dialogVisible = false">取 消</el-button>
            </span>
          </el-dialog>
        </el-container>
      </el-tab-pane>
      <el-tab-pane name="projectInfoPanel">
        <span slot="label"><i class="el-icon-info"/> 项目信息</span>
        <el-form ref="projectForm" :model="projectForm" :rules="projectFormRules" label-width="120px" class="project-form">
          <el-form-item label="项目名称" prop="name">
            <el-input v-model="projectForm.name" placeholder="文档名称" />
          </el-form-item>
          <el-form-item label="Git链接" prop="gitUrl">
            <el-input v-model="projectForm.gitUrl" placeholder="如：https://gitee.com/xxx/myproject.git" />
          </el-form-item>
          <el-form-item label="本地存放路径" prop="localGitPath">
            <el-input v-model="projectForm.localGitPath" placeholder="如：D:/IdeaProjects/myproject" />
          </el-form-item>
          <el-form-item label="菜单默认展开" prop="expandAll">
            <el-checkbox v-model="projectForm.expandAll" />
          </el-form-item>
          <el-form-item label="创建时间">
            {{ projectForm.gmtCreate }}
          </el-form-item>
          <el-form-item label="修改时间">
            {{ projectForm.gmtUpdate }}
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="saveProjectInfo('projectForm')">保存</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<style>
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
  .project-form {width: 600px;padding: 30px;}
  .docFormCls .el-form-item {margin-bottom: 10px;}
</style>
<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
  components: { mavonEditor },
  data() {
    return {
      params: {},
      docForm: {
        name: '',
        parentName: '',
        isShow: '',
        content: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入文档名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ]
      },
      projectForm: {
        name: '',
        gitUrl: '',
        localGitPath: '',
        expandAll: true,
        gmtCreate: '',
        gmtUpdate: ''
      },
      projectFormRules: {
        name: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        gitUrl: [
          { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
        ],
        localGitPath: [
          { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
        ]
      },
      templateList: [],
      addFolderForm: {
        name: ''
      },
      addFolderFormRules: {
        name: [
          { required: true, message: '请输入目录名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ]
      },
      docFormVisible: false,
      dialogVisible: false,
      filterText: '',
      /*
      [{
        id: 1,
        label: 'Level one 1',
        children: [{
          id: 4,
          label: 'Level two 1-1',
          children: [{
            id: 9,
            label: 'Level three 1-1-1'
          }, {
            id: 10,
            label: 'Level three 1-1-2'
          }]
        }]
      }]
      */
      data: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      currentTreeId: 0,
      useOnchange: false,
      isChanged: false,
      isShowReleaseBtn: false
    }
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'initData',
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.params.projectId = this.params.id
    this.initData()
  },
  methods: {
    initData: function() {
      this.params = this.$route.query
      this.docFormVisible = false
      this.resetForms()
      this.initTemplates()
      this.initProjectInfo(function() {
        this.loadTree()
      })
    },
    // 加载树
    loadTree: function() {
      const projectId = this.params.id
      if (projectId) {
        this.params.projectId = projectId
        const param = {}
        param.projectId = projectId
        this.post('doc.treegrid.page', param, function(resp) {
          const respData = resp.data
          const treeData = this.convertToTreeData(respData.rows, 0)
          this.data = treeData
          this.$nextTick(() => {
            if (this.currentTreeId) {
              this.$refs.tree.setCurrentKey(this.currentTreeId)
            }
            const allNodes = this.$refs.tree.store._getAllNodes()
            for (let i = 0; i < allNodes.length; i++) {
              allNodes[i].expanded = this.projectForm.expandAll
            }
          })
        })
      }
    },
    initTemplates() {
      this.post('template.listall', {}, function(resp) {
        this.templateList = resp.data
      })
    },
    initProjectInfo(callback) {
      this.post('project.detail.get', { id: this.params.id }, function(resp) {
        const projectInfo = resp.data
        Object.assign(this.projectForm, projectInfo)
        this.projectForm.expandAll = projectInfo.menuExpandall === 1
        this.isShowReleaseBtn = this.showReleaseBtn()
        callback && callback.call(this)
      })
    },
    resetForms() {
      const refs = this.$refs
      for (const formRef in refs) {
        if (refs[formRef].$el.localName === 'form') {
          refs[formRef].resetFields()
        }
      }
    },
    // 树搜索
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    // 树点击事件
    onNodeClick(data) {
      if (!data.parentId) {
        return false
      }
      const docId = data.id
      if (docId === this.currentTreeId) {
        return
      }
      if (!this.docFormVisible) {
        this.docFormVisible = true
      }
      if (this.isChanged) {
        this.$confirm('内容未保存', '提示', {
          confirmButtonText: '立即保存',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.submitForm(function() {
            this.doClickNode(docId)
          })
        }).catch(() => {
          this.$refs.tree.setCurrentKey(this.currentTreeId)
        })
      } else {
        this.doClickNode(docId)
      }
    },
    doClickNode: function(docId) {
      this.currentTreeId = docId
      this.$refs.docForm.resetFields()
      this.post('doc.detail.get', { id: docId }, function(resp) {
        this.disableOnChange()
        const docDetail = resp.data
        docDetail.isShow = docDetail.isShow + ''
        Object.assign(this.docForm, docDetail)
        this.$nextTick(() => {
          this.enableOnChange()
        })
      })
    },
    /**
     * 数组转成树状结构
     * @param data 数据结构 [{
            "_parentId": 14,
            "gmtCreate": "2019-01-15 09:44:38",
            "gmtUpdate": "2019-01-15 09:44:38",
            "id": 15,
            "isShow": 1,
            "name": "用户注册",
            "orderIndex": 10000,
            "parentId": 14
        },...]
     * @param pid 初始父节点id，一般是0
     * @return [{
          label: '一级 1',
          children: [{
            label: '二级 1-1',
            children: [{
              label: '三级 1-1-1'
            }]
          }]
        }
     */
    convertToTreeData(data, pid) {
      const result = []
      let temp = []
      for (let i = 0; i < data.length; i++) {
        if (data[i].parentId === pid) {
          const obj = { 'label': data[i].name, 'id': data[i].id, 'parentId': data[i].parentId }
          temp = this.convertToTreeData(data, data[i].id)
          if (temp.length > 0) {
            obj.children = temp
          }
          result.push(obj)
        }
      }
      return result
    },
    // 插入模板
    insertApi(item) {
      const insertVal = item.content
      this.docForm.content = this.docForm.content + insertVal
      console.log(this.docForm.content)
    },
    // 添加目录
    addFolder: function() {
      this.dialogVisible = true
    },
    closeAddFolderDlg: function() {
      this.$refs.addFolderForm.resetFields()
    },
    // 添加文档
    addNode: function(data) {
      this.$router.push({ path: '/newDoc',
        query: {
          parentId: data.id,
          projectId: this.params.projectId,
          parentName: data.label
        }})
    },
    // 删除文档
    delNode: function(data) {
      this.confirm(`确认要删除[${data.label}]吗？`, function(done) {
        this.post('doc.delete', { id: data.id }, function() {
          done()
          this.tip('删除成功')
          this.loadTree()
        })
      })
    },
    // 修改文档内容
    submitForm(callback) {
      this.$refs['docForm'].validate((valid) => {
        if (valid) {
          this.post('doc.page.update', this.docForm, function(resp) {
            this.isChanged = false
            this.tip('修改成功')
            this.loadTree()
            callback && callback.call(this)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    showReleaseBtn: function() {
      return this.projectForm.gitUrl && this.projectForm.localGitPath
    },
    // 提交新增目录
    submitFolderForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const param = this.addFolderForm
          param.projectId = this.params.projectId
          this.post('doc.folder.create', param, function(resp) {
            this.dialogVisible = false
            this.loadTree()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    disableOnChange: function() {
      this.useOnchange = false
      this.isChanged = false
    },
    enableOnChange: function() {
      this.useOnchange = true
      this.isChanged = false
    },
    onContentChange: function(val) {
      if (this.useOnchange) {
        this.isChanged = true
      }
    },
    onContentSave: function() {
      this.submitForm()
    },
    // 保存项目信息
    saveProjectInfo(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const param = this.projectForm
          param.menuExpandall = param.expandAll ? 1 : 0
          this.post('project.update', param, function() {
            this.tip('修改成功')
            this.initProjectInfo()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    // 发布文档
    releaseDoc() {
      this.confirm('确认要发布文档吗？', function(done) {
        const loading = this.$loading({
          lock: true,
          text: '发布中...',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        const param = { projectId: this.params.projectId }
        this.post('project.release', param, function() {
          done()
          loading.close()
          this.tip('发布成功')
        })
      })
    }
  }
}
</script>


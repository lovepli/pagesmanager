<template>
  <div class="app-container">
    <el-container>
      <el-aside style="min-height: 300px;width: 250px;">
        <el-input v-model="filterText" placeholder="搜索文档..." style="margin-bottom:10px;" size="mini" clearable>
          <i slot="prefix" class="el-input__icon el-icon-search"/>
        </el-input>
        <el-tree
          ref="treeView"
          :data="data"
          :props="defaultProps"
          :filter-node-method="filterNode"
          :highlight-current="true"
          :expand-on-click-node="false"
          empty-text="无数据"
          node-key="id"
          class="filter-tree"
          @node-click="onNodeClick"
        >
          <span slot-scope="{ node, data }" class="custom-tree-node">
            <span v-if="data.label.length < 9">{{ data.label }}</span>
            <span v-else>
              <el-tooltip :content="data.label" class="item" effect="light" placement="right">
                <span>{{ data.label.substring(0, 9) + '...' }}</span>
              </el-tooltip>
            </span>
          </span>
        </el-tree>
      </el-aside>
      <el-main v-show="docFormVisible" style="padding-top: 2px">
        <el-form ref="docForm" :inline="true" :model="docForm">
          <mavon-editor
            v-model="docForm.content"
            :toolbarsFlag="false"
            :subfield="false"
            defaultOpen="preview"
          />
        </el-form>
      </el-main>
    </el-container>
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
      templateList: [],
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
      }
    }
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'initData',
    filterText(val) {
      this.$refs.treeView.filter(val)
    }
  },
  created() {
    this.initData()
  },
  methods: {
    initData: function() {
      this.params = this.$route.query
      this.docFormVisible = false
      this.resetForms()
      this.loadTree()
    },
    // 加载树
    loadTree: function() {
      const projectId = this.params.id
      if (projectId) {
        this.params.projectId = projectId
        const param = {}
        param.projectId = projectId
        this.post('nologin.doc.treegrid.page', param, function(resp) {
          const pageTreeGrid = resp.data
          const respData = pageTreeGrid.pageEasyui
          const treeData = this.convertToTreeData(respData.rows, 0)
          this.data = treeData
          const expandAll = pageTreeGrid.properties.expandAll
          this.$nextTick(() => {
            const allNodes = this.$refs.treeView.store._getAllNodes()
            for (let i = 0; i < allNodes.length; i++) {
              allNodes[i].expanded = expandAll
            }
          })
        })
      }
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
    onNodeClick(data, node, tree) {
      if (!data.parentId) {
        return false
      }
      if (!this.docFormVisible) {
        this.docFormVisible = true
      }
      const docId = data.id
      this.$refs.docForm.resetFields()
      this.post('nologin.doc.detail.get', { id: docId }, function(resp) {
        const docDetail = resp.data
        docDetail.isShow = docDetail.isShow + ''
        Object.assign(this.docForm, docDetail)
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
    }
  }
}
</script>


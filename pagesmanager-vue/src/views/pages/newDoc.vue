<template>
  <div class="app-container">
    <el-button
      type="text"
      icon="el-icon-back"
      @click="() => {
        this.$router.push({ path: `/docManager`, query: { id: $route.query.projectId }})
    }">返回</el-button>
    <el-container>
      <el-form ref="docForm" :inline="true" :model="docForm" :rules="rules" style="width: 100%">
        <el-form-item prop="name">
          <el-input v-model="docForm.name" class="doc-title" placeholder="文档名称" style="width: 600px;">
            <template slot="prepend">{{ docForm.parentName }}  --></template>
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="docForm.isShow" true-label="1" false-label="0">是否显示</el-checkbox>
        </el-form-item>
        <br>
        <el-form-item label="插入模板" style="margin-bottom:0">
          <el-button v-for="item in templateList" :key="item.id" type="text" @click="insertApi(item)" >{{ item.name }}</el-button>
        </el-form-item>
        <mavon-editor v-model="docForm.content" :boxShadow="false" style="min-height: 500px"/>
        <el-form-item>
          <el-button type="primary" style="margin-top: 10px;" @click="submitForm('docForm')">保存</el-button>
        </el-form-item>
      </el-form>
    </el-container>
  </div>
</template>
<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
  components: { mavonEditor },
  data() {
    return {
      params: {},
      docForm: {
        parentId: '',
        name: '',
        parentName: '',
        isShow: '1',
        content: ''
      },
      rules: {
        name: [
          { required: true, message: '请输入文档名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ]
      },
      templateList: []
    }
  },
  created() {
    this.params = this.$route.query
    this.docForm.parentName = this.params.parentName
    this.docForm.parentId = this.params.parentId
    this.docForm.projectId = this.params.projectId

    this.initTemplates()
  },
  methods: {
    insertApi(item) {
      const insertVal = item.content
      this.docForm.content = this.docForm.content + insertVal
    },
    initTemplates() {
      this.post('template.listall', {}, function(resp) {
        this.templateList = resp.data
      })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // console.log(this.docForm)
          this.post('doc.page.create', this.docForm, function(resp) {
            this.tip('添加成功')
            this.$router.push({ path: `/docManager`, query: { id: this.params.projectId }})
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>


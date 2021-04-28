<template>
  <div class="app-container">
    <h3>创建项目</h3>
    <el-form ref="projectForm" :model="projectForm" :rules="projectFormRules" label-width="120px" class="project-form">
      <el-form-item label="项目名称" prop="name">
        <el-input v-model="projectForm.name" placeholder="项目名称" />
      </el-form-item>
      <el-form-item label="Git链接" prop="gitUrl">
        <el-input v-model="projectForm.gitUrl" placeholder="如：https://gitee.com/xxx/myproject.git" />
      </el-form-item>
      <el-form-item label="本地存放路径" prop="localGitPath">
        <el-input v-model="projectForm.localGitPath" placeholder="如：D:/IdeaProjects/myproject" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="saveProjectInfo('projectForm')">创 建</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<style scoped>
  .project-form {width: 600px;}
</style>
<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'

export default {
  components: { mavonEditor },
  data() {
    return {
      params: {},
      projectForm: {
        name: '',
        gitUrl: '',
        localGitPath: ''
      },
      projectFormRules: {
        name: [
          { required: true, message: '请输入项目名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        gitUrl: [
          { required: true, message: '请输入Git链接', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
        ],
        localGitPath: [
          { required: true, message: '请输入本地存放路径', trigger: 'blur' },
          { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
  },
  methods: {
    // 保存项目信息
    saveProjectInfo(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const param = this.projectForm
          this.post('project.create', param, function() {
            window.location.href = '/'
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


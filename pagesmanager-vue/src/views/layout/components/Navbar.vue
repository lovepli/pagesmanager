<template>
  <el-menu
    :default-active="defaultActive"
    class="nav-menu"
    background-color="#304156"
    text-color="#fff"
    active-text-color="#409EFF"
    mode="horizontal">
    <!--<hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>-->
    <!--<breadcrumb />-->
    <el-menu-item index="home" @click="()=>{$router.push('/')}">
      首页
    </el-menu-item>
    <el-menu-item v-for="(item) in projectList" :key="item.id" :index="item.id.toString()" @click="selectProject(item)">
      {{ item.name }}
    </el-menu-item>
    <el-menu-item index="newProject" style="float: right">
      <el-button
        type="success"
        size="medium"
        icon="el-icon-plus"
        @click="()=>{ $router.push('/newProject') }">
        新建项目
      </el-button>
    </el-menu-item>
  </el-menu>
</template>
<style>
  .nav-menu, .el-menu-item i{color: #fff}
</style>
<script>

export default {
  data() {
    return {
      params: {},
      projectList: [],
      defaultActive: 'home'
    }
  },
  created() {
    const id = this.$route.query.id
    if (id) {
      this.defaultActive = id
    }
    this.post('project.listall', {}, function(resp) {
      const projects = resp.data
      this.projectList = projects
    })
  },
  methods: {
    selectProject(item) {
      this.$router.replace({ path: `/docManager`, query: { id: item.id }})
    }
  }
}
</script>

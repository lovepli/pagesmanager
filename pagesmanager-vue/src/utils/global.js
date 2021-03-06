/*
注册全局方法
 */
import Vue from 'vue'
import axios from 'axios'
import { getToken, removeToken } from './auth'

// 创建axios实例
const client = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 50000 // 请求超时时间
})

Object.assign(Vue.prototype, {
  /**
   * 请求接口
   * @param uri uri，如：goods.get,goods.get/1.0
   * @param data 请求数据
   * @param callback 成功时回调
   * @param errorCallback 错误时回调
   */
  post: function(uri, data, callback, errorCallback) {
    const that = this
    const paramStr = JSON.stringify(data)
    if (!uri.endsWith('/')) {
      uri = uri + '/'
    }
    if (!uri.startsWith('/')) {
      uri = '/' + uri
    }
    const jwt = getToken()
    if (jwt) {
      client.defaults.headers.common['Authorization'] = 'Bearer ' + getToken()
    }
    client.post(uri, {
      data: encodeURIComponent(paramStr)
    }).then(function(response) {
      const resp = response.data
      const code = resp.code
      if (!code || code === '-9') {
        that.$message.error('系统错误')
        return
      }
      if (code === '-100' || code === '18' || code === '21') { // 未登录
        that.logout()
        return
      }
      if (code === '0') { // 成功
        callback && callback.call(that, resp)
      } else {
        that.$message.error(resp.msg)
      }
    }).catch(function(error) {
      console.error('err' + error) // for debug
      errorCallback && errorCallback(error)
      that.$message.error(error.message)
    })
  },
  /**
   * tip，使用方式：this.tip('操作成功')，this.tip('错误', 'error')
   * @param msg 内容
   * @param type success / info / warning / error
   * @param stay 停留几秒，默认3秒
   */
  tip: function(msg, type, stay) {
    stay = parseInt(stay) || 3
    this.$message({
      message: msg,
      type: type || 'success',
      duration: stay * 1000
    })
  },
  /**
   * 提醒框
   * @param msg 消息
   * @param okHandler 成功回调
   * @param cancelHandler
   */
  confirm: function(msg, okHandler, cancelHandler) {
    const that = this
    this.$confirm(msg, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
      beforeClose: (action, instance, done) => {
        if (action === 'confirm') {
          okHandler.call(that, done)
        } else if (action === 'cancel') {
          if (cancelHandler) {
            cancelHandler.call(that, done)
          } else {
            done()
          }
        } else {
          done()
        }
      }
    }).catch(function() {})
  },
  logout: function() {
    removeToken()
    this.$router.push({ path: `/login?redirect=${this.$route.fullPath}` })
  }
})

<template>
  <div class="the-header" style="display: flex; justify-content:space-between;">
    <!--图标-->
    <div class="header-logo" @click="goHome">
      <svg class="icon" aria-hidden="true">
        <use xlink:href="#icon-erji"></use>
      </svg>
      <span>{{musicName}}</span>
    </div>
    <!-- 左侧菜单 -->
  
    <el-menu class="el-menu" :default-active="activeIndex" style="border: 0; width: 500px;">
      <el-menu-item class="el-item" v-for="item in navMsg" :key="item.path" @click="goPage(item.path, item.name)">
        {{item.name}}
      </el-menu-item>
    </el-menu>
    <!-- 搜索框 -->
    <div class="navbar" ref="change" style="display: flex; justify-content: center; align-items: center;">
      <div class="header-search">
        <input type="text" placeholder="搜索音乐" @keyup.enter="goSearch()" v-model="keywords">
        <div class="search-btn"  @click="goSearch()" >
          <!-- <svg class="icon" aria-hidden="true">
            <use xlink:href="#icon-sousuo"></use>
          </svg> -->
          <i class="el-icon-search icon2" aria-hidden="true">
            <use xlink:href="#icon-sousuo"></use>
          </i>
        </div>
      </div>
    </div>
    <!-- 右侧菜单  -->
    <el-menu class="el-menu" style="border: 0; width: 200px;" v-if="!loginIn">
      <el-menu-item class="el-item2"  v-for="item in loginMsg" :key="item.type" @click="goPage(item.path, item.name)">
        {{item.name}}
      </el-menu-item>
    </el-menu>
   
    <!--设置-->
    <div class="header-right" v-show="loginIn">
      <div id="user">
        <img :src="attachImageUrl(avator)">
      </div>
      <ul class="menu">
        <li v-for="(item, index) in menuList" :key="index" @click="goMenuList(item.path)">{{item.name}}</li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { mapGetters } from 'vuex'
import { navMsg, loginMsg, menuList } from '../assets/data/header'

export default {
  name: 'the-header',
  mixins: [mixin],
  data () {
    return {
      musicName: '墨樂阁',
      navMsg: [], // 左侧导航栏
      loginMsg: [], // 右侧导航栏
      menuList: [], // 用户下拉菜单项
      keywords: ''
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'activeName',
      'avator',
      'username',
      'loginIn',

    ])
  },
  created () {
    this.navMsg = navMsg
    this.loginMsg = loginMsg
    this.menuList = menuList
  },
  mounted () {
    document.querySelector('#user').addEventListener('click', function (e) {
      document.querySelector('.menu').classList.add('show')
      e.stopPropagation()// 关键在于阻止冒泡
    }, false)
    // 点击“菜单”内部时，阻止事件冒泡。(这样点击内部时，菜单不会关闭)
    document.querySelector('.menu').addEventListener('click', function (e) {
      e.stopPropagation()
    }, false)
    document.addEventListener('click', function () {
      document.querySelector('.menu').classList.remove('show')
    }, false)
  },
  methods: {
    goHome () {
      this.$router.push({path: '/'})
    },
    goPage (path, value) {
      document.querySelector('.menu').classList.remove('show')
      this.changeIndex(value)
      if (!this.loginIn && path === '/my-music') {
        this.notify('请先登录', 'warning')
      } else {
        this.$store.commit('setAnotherUserId', '')
        this.$store.commit('setVisit', false)
        this.$router.push({path: path})
      }
    },
    changeIndex (value) {
      this.$store.commit('setActiveName', value)
    },
    goMenuList (path) {
      if (path === 0) {
        this.$store.commit('setIsActive', false)
      }
      document.querySelector('.menu').classList.remove('show')
      if (path) {
        this.$router.push({path: path})
      }
       else {
        this.$store.commit('setLoginIn', false)
        this.$router.go(0)
      }
    },
    goSearch () {
      this.$store.commit('setSearchword', this.keywords)
      this.$router.push({path: '/search', query: {keywords: this.keywords}})
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/the-header.scss';
</style>

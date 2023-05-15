<template>
  <div class="home">
    <!--轮播图-->
    <swiper/>
    <!--热门歌单/歌手-->
    <div class="section" v-for="(item, index) in songsList" :key="index">
      <div class="section-title">{{item.name}}</div>
      <content-list :contentList="item.list"></content-list>
    </div>
  </div>
</template>

<script>
import Swiper from '../components/Swiper'
import ContentList from '../components/ContentList'
import { mapGetters } from 'vuex'
import { getSongList, getAllSinger, getSongListRecommend } from '../api/index'

export default {
  name: 'home',
  components: {
    Swiper,
    ContentList
  },
  data () {
    return {
      songsList: [
        {name: '推荐歌单', list: []},
        {name: '热门歌手', list: []}
      ]
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'loginIn'
    ])
  },
  created () {
    // 获取歌单列表
    this.getSongList('songList')
    // 获取歌手列表
    this.getSinger('singer')
  },
  methods: {
    getSongList (path) {
      if(this.loginIn){
        getSongListRecommend(this.userId)
          .then(res => {  //获取前十条歌单
            this.songsList[0].list = res.data.slice(0, 10)
          })
          .catch(err => {
            console.log(err)
          })
      }else {
        getSongList()
        .then(res => {  //获取前十条歌单
          this.songsList[0].list = res.data.slice(0, 10)
        })
        .catch(err => {
          console.log(err)
        })
      }
    },
    getSinger () {
      getAllSinger().then(res => {    //获取前十名歌手
        this.songsList[1].list = res.data.slice(0, 10)
      })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>

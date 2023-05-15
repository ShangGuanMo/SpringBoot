<template>
  <div class="my-music">
    <div class="album-slide">
      <div class="album-img" >
        <img :src=attachImageUrl(avator) alt="">
        <div class="guanzhu">
          <span>
            关注
          </span>
        </div>
      </div>
      <ul class="album-info">
        <li>昵称： {{username}}</li>
        <li>性别： {{userSex}}</li>
        <li>生日： {{birth}}</li>
        <li>故乡： {{location}}</li>
      </ul>
    </div>
    <div class="album-content">
      <div class="album-title">个性签名: {{introduction}}</div>
      <div class="songs-body">
        <album-content :songList="collectList">
          <template slot="title">我的收藏</template>
        </album-content>
      </div>
    </div>
  </div>
</template>

<script>
import {mixin} from '../mixins'
import { mapGetters } from 'vuex'
import AlbumContent from '../components/AlbumContent'
import { getCollectionOfUser, getUserOfId, getSongOfId } from '../api/index'

export default {
  name: 'my-music',
  components: {
    AlbumContent
  },
  mixins: [mixin],
  data () {
    return {
      avator: '',
      username: '',
      userSex: '',
      birth: '',
      location: '',
      introduction: '',
      collection: [], // 存放收藏的歌曲ID
      collectList: [], // 收藏的歌曲
    }
  },
  computed: {
    ...mapGetters([
      'userId',
      'id',
      'listOfSongs', // 存放的音乐
      'anotherUserId',
      'visit'
    ])
  },
  mounted () {
    // 如果是visit为true，则表示进入的是其他用户的主页
    if(this.visit){
      this.getMsg(this.anotherUserId)
      this.getCollection(this.anotherUserId)
    }else{
      this.getMsg(this.userId)
      this.getCollection(this.userId)
    }
  },
  methods: {
    getMsg (id) {
      getUserOfId(id)
        .then(res => {
          this.username = res.data[0].username
          this.getuserSex(res.data[0].sex)
          this.birth = this.attachBirth(res.data[0].birth)
          this.introduction = res.data[0].introduction
          this.location = res.data[0].location
          this.avator = res.data[0].avator
        })
        .catch(err => {
          console.log(err)
        })
    },
    getuserSex (sex) {
      if (sex === 0) {
        this.userSex = '女'
      } else if (sex === 1) {
        this.userSex = '男'
      }
    },
    // 收藏的歌曲ID
    getCollection (userId) {
      getCollectionOfUser(userId)
        .then(res => {
          this.collection = res.data
          // 通过歌曲ID获取歌曲信息
          for (let item of this.collection) {
            this.getCollectSongs(item.songId)
          }
          this.$store.commit('setListOfSongs', this.collectList)
          
        })
        .catch(err => {
          console.log(err)
        })
        // console.log(this.listOfSongs)
    },
    // 获取收藏的歌曲
    getCollectSongs (id) {
      getSongOfId(id)
        .then(res => {
          this.collectList.push(res.data)
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/my-music.scss';

</style>

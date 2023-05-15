const user = {
  state: {
    userId: '',
    username: '',
    avator: '',
    anotherUserId: '',
    visit: ''
  },
  getters: {
    userId: state => {
      let userId = state.userId
      if (!userId) {
        userId = JSON.parse(window.localStorage.getItem('userId') || null)
      }
      return userId
    },
    username: state => {
      let username = state.username
      if (!username) {
        username = JSON.parse(window.localStorage.getItem('username') || null)
      }
      return username
    },
    avator: state => {
      let avator = state.avator
      if (!avator) {
        avator = JSON.parse(window.localStorage.getItem('avator') || null)
      }
      return avator
    },
    anotherUserId: state => {
      let anotherUserId = state.anotherUserId
      if (!anotherUserId) {
        anotherUserId = JSON.parse(window.localStorage.getItem('anotherUserId') || null)
      }
      return anotherUserId
    },
    visit: state => {
      let visit = state.visit
      if (!visit) {
        visit = JSON.parse(window.localStorage.getItem('visit') || null)
      }
      return visit
    }
  },
  mutations: {
    setUserId: (state, userId) => {
      state.userId = userId
      window.localStorage.setItem('userId', JSON.stringify(userId))
    },
    setUsername: (state, username) => {
      state.username = username
      window.localStorage.setItem('username', JSON.stringify(username))
    },
    setAvator: (state, avator) => {
      state.avator = avator
      window.localStorage.setItem('avator', JSON.stringify(avator))
    },
    setAnotherUserId: (state, anotherUserId) => {
      state.anotherUserId = anotherUserId
      window.localStorage.setItem('anotherUserId', JSON.stringify(anotherUserId))
    },
    setVisit: (state, visit) => {
      state.visit = visit
      window.localStorage.setItem('visit', JSON.stringify(visit))
    }
  },
  actions: {}
}

export default user

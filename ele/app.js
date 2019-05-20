//app.js
App({
  onLaunch: function () {
    var that = this;

    wx.getStorage({
      key: 'sessionId',
      success: function (res) {
          that.globalData.sessionId = res.data
      },
    })
  },
  globalData: {
    latitude:'',
    longitude:'',
    fromEditAddress:0,
    userInfo: '',
    hasUserInfo:false,
    sessionId:'',
    // host:'https://xbxmxx.top:8443',
    host:'http://localhost',
    // host: 'http://localhost:8443',
    inCart:[],
    locationOnShop:'',
    shopIcon:''
  }
})
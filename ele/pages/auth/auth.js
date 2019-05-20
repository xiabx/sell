// pages/auth/auth.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    noshowlocationbutton:false,
    noshowuserinfobutton:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;
    wx.getSetting({
      success:function(res){
        if (res.authSetting["scope.userLocation"] == true){
          that.setData({
            noshowlocationbutton:true
          })
        }
        if (res.authSetting["scope.userLocation"] == true){
          that.setData({
            noshowuserinfobutton: true
          })
        }
      }
    })
  },
  checkUserInfo:function(res){
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          app.globalData.hasUserInfo = true

          if (this.data.noshowlocationbutton && this.data.noshowuserinfobutton ){
            wx.switchTab({
              url: "/pages/shop/shop",
            })
          }
        }
      })
  },
  locationAuth:function(res){
    //位置授权
    if (res.detail.authSetting["scope.userLocation"] == false){
      wx.showToast({
        title: '授权失败，请重新授权',
        icon:'none'
      })
    }else{
      if (this.data.noshowlocationbutton && this.data.noshowuserinfobutton) {
        wx.switchTab({
          url: "/pages/shop/shop",
        })
      }
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})
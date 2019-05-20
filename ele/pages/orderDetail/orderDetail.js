// pages/orderDetail/orderDetail.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    info:{},
    wxTimerList: {},
    minutes:'',
    seconds:'',
    currentTimeId:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    this.setData({
      orderId:options.orderId
    })
  },
  toComment:function(){
    wx.navigateTo({
      url: '/pages/comment/comment?orderId='+this.data.info.orderId,
    })
  },
  toCancel:function(){
    var that = this;
    wx.showLoading({
      title: '取消中',
    })
    wx.request({
      url: app.globalData.host +'/buyer/order/cancel',
      data:{
        orderId: this.data.info.orderId,
        sessionId:app.globalData.sessionId
      },
      success:function(res){
        if(res.data.code == 0){
          wx.request({
            url: app.globalData.host +'/buyer/order/detail',
            data: {
              sessionId: app.globalData.sessionId,
              orderId: that.data.orderId
            },
            success: function (res) {
              that.setData({
                info: res.data.data
              })
            }
          })
        }
        wx.hideLoading();
      }
    })
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
    wx.request({
      url: app.globalData.host +'/buyer/order/detail',
      data: {
        sessionId: app.globalData.sessionId,
        orderId: this.data.orderId
      },
      success: function (res) {
        that.setData({
          info: res.data.data
        })

        //当订单为未支付时  倒计时
        if (that.data.info.orderStatusCode == 0) {
          var end = Date.now() + 60000 * that.data.info.minute + 1000 * that.data.info.second;
          var pre = 0;
          function fun() {
            var value = end - Date.now();
            var secondsNum = parseInt(value / 1000);
            if (pre != secondsNum) {
              that.setData({
                minutes: parseInt(secondsNum / 60) >= 10 ? parseInt(secondsNum / 60) : "0" + parseInt(secondsNum / 60),
                seconds: parseInt(secondsNum % 60) >= 10 ? parseInt(secondsNum % 60) : "0" + parseInt(secondsNum % 60),
              })
              pre = secondsNum;
            }
            if (value <= 0) {
              clearInterval(id);
              // console.log("ok");
              wx.showToast({
                title: '超时未支付，订单已取消',
                icon: 'none'
              })
              wx.showNavigationBarLoading();
              wx.request({
                url: app.globalData.host + '/buyer/order/detail',
                data: {
                  sessionId: app.globalData.sessionId,
                  orderId: that.data.orderId
                },
                success: function (res) {
                  that.setData({
                    info: res.data.data
                  })
                }
              })
              wx.hideNavigationBarLoading();
            }
          }
          var id = setInterval(fun, 200);
          that.setData({
            currentTimeId: id
          })
        }
      }
    })
  },
  toPay:function(event){
    wx.redirectTo({
      url: '/pages/pay/pay?orderId=' +this.data.orderId + '&shopName=' + this.data.info.shopName,
    })
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
    clearInterval(this.data.currentTimeId);
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    //清除之前定时器 启用新的定时器
    clearInterval(this.data.currentTimeId);
    wx.showNavigationBarLoading();
    var that = this;
    wx.request({
      url: app.globalData.host +'/buyer/order/detail',

      data: {
        sessionId: app.globalData.sessionId,
        orderId: this.data.orderId
      },
      success: function (res) {
        that.setData({
          info: res.data.data
        })
        //当订单为未支付时  倒计时
        if (that.data.info.orderStatusCode == 0) {
          var end = Date.now() + 60000 * that.data.info.minute + 1000 * that.data.info.second;
          var pre = 0;
          function fun() {
            var value = end - Date.now();
            var secondsNum = parseInt(value / 1000);
            if (pre != secondsNum) {
              that.setData({
                minutes: parseInt(secondsNum / 60) >= 10 ? parseInt(secondsNum / 60) : "0" + parseInt(secondsNum / 60),
                seconds: parseInt(secondsNum % 60) >= 10 ? parseInt(secondsNum % 60) : "0" + parseInt(secondsNum % 60),
              })
              pre = secondsNum;
            }
            if (value <= 0) {
              clearInterval(id);
              console.log("ok");
              wx.showToast({
                title: '超时未支付，订单已取消',
                icon: 'none'
              })
              wx.showNavigationBarLoading();
              wx.request({
                url: app.globalData.host + '/buyer/order/detail',
                data: {
                  sessionId: app.globalData.sessionId,
                  orderId: that.data.orderId
                },
                success: function (res) {
                  that.setData({
                    info: res.data.data
                  })
                }
              })
              wx.hideNavigationBarLoading();
            }
          }
          var id = setInterval(fun, 200);
          that.setData({
            currentTimeId:id
          })
        }
        wx.stopPullDownRefresh();
        wx.hideNavigationBarLoading();
      }
    })

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
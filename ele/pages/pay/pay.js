// pages/pay/pay.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    selectType:0,
    amount:0,
    minutes:'',
    seconds:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;

    var end = Date.now() + 60000 * 15;
    var pre = 0;
    function fun() {
      var value = end - Date.now();
      var secondsNum = parseInt(value / 1000);
      if (pre != secondsNum) {
        that.setData({
          minutes: parseInt(secondsNum / 60),
          seconds: parseInt(secondsNum % 60)
        })
        pre = secondsNum;
        
      }
      if (value <= 0) {
        clearInterval(id);
        console.log("ok");
        wx.showModal({
          title: '超时提示',
          content: '超时未支付，订单已自动取消',
          showCancel: false,
          success: function (res) {
            if (res.confirm == true) {
              wx.switchTab({
                url: '/pages/shop/shop',
              })
            }
          }
        })
      }
    }
    var id = setInterval(fun, 200);
    that.setData({
      timeId:id
    })


 
    wx.request({
      url: app.globalData.host +'/buyer/order/amount',
      data:{
        sessionId:app.globalData.sessionId,
        orderId: options.orderId
      },
      success:function(res){
        that.setData({
          orderId: options.orderId,
          amount: res.data.data.amount
        })
      }
    })
  },
  //设置选择的图片显示
  selectPayType:function(event){
    if (event.currentTarget.dataset.type == 1){
      this.setData({
        selectType:1
      })
    }else{
      this.setData({
        selectType: 2
      })
    }
  },
  pay:function(){
    var that = this;
    if(this.data.selectType == 0){
      wx.showToast({
        icon:'none',
        title: '请先选择支付方式，使用意念支付',
      })
    }else if (this.data.selectType == 1){
      wx.showToast({
        icon: 'none',
        title: '抱歉，暂不支持微信支付',
      })
    }else{
      wx.showLoading({
        title: '请稍候',
      })
      wx.request({
        url: app.globalData.host +'/buyer/order/paySuccess',
        data:{
          orderId:this.data.orderId
        },
        success:function(res){
          if (res.data.code == 1){
            wx.showModal({
              content: '支付成功',
              showCancel: false,
              success: function (res) {
                if (res.confirm == true) {
                  wx.redirectTo({
                    url: '/pages/orderDetail/orderDetail?orderId='+that.data.orderId,
                  })
                }
              }
            })
          }
        }
      })
      wx.hideLoading();
    }
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
    clearInterval(this.data.timeId);
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
// pages/order/order.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    orderList:[],
    page:1,
    isLastPage:false,
    noOrder:false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

  },
  //我是前往订单详情的方法呦~~~~
  toDetail:function(event){
    wx.navigateTo({
      url: '/pages/orderDetail/orderDetail?orderId=' + event.currentTarget.dataset.orderid,
    });
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
    that.setData({
      page:1
    })
    wx.request({
      url: app.globalData.host +'/buyer/order/list',
      data:{
        sessionId:app.globalData.sessionId,
        page:1
      },
      success:function(res){
        if (res.data.data.list[0] == null){
          that.setData({
            noOrder:true
          })
        }else{
          that.setData({
            orderList: res.data.data.list,
            page: that.data.page + 1,
            isLastPage: res.data.data.isLastPage
          })
        }
      }
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
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading();
    this.setData({
      page: 1,
    })
    var that = this;
    wx.request({
      url: app.globalData.host +'/buyer/order/list',
      data: {
        sessionId: app.globalData.sessionId,
        page: 1
      },
      success: function (res) {
        that.setData({
          orderList: res.data.data.list,
          page: that.data.page + 1,
          isLastPage: res.data.data.isLastPage
        })
        wx.stopPullDownRefresh();
        wx.hideNavigationBarLoading();
      }
    })

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    if(this.data.noOrder == false){
      wx.showNavigationBarLoading({});
      if (this.data.isLastPage == false) {
        var that = this;
        wx.request({
          url: app.globalData.host +'/buyer/order/list',
          data: {
            sessionId: app.globalData.sessionId,
            page: this.data.page
          },
          success: function (res) {
            var list = that.data.orderList;
            var newList = list.concat(res.data.data.list);
            that.setData({
              orderList: newList,
              page: that.data.page + 1,
              isLastPage: res.data.data.isLastPage
            })
          }
        })
      }
      wx.hideNavigationBarLoading();
    }
   
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})
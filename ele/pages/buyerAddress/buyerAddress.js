// pages/buyerAddress/buyerAddress.js
var QQMapWX = require('../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[],
    fromMy:1,
    sessionId:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      fromMy: options.fromMy
    })
  },
  //回到订单界面
  returnOrder:function(event){
    if(this.data.fromMy == 0){
      var address = this.data.list[event.currentTarget.dataset.index]
      var pages = getCurrentPages();
      var prevPage = pages[pages.length - 2];   //上一个页面
      prevPage.setData({
        location: address.location,
        detail: address.detail,
        buyerName: address.buyerName,
        buyerPhone: address.buyerPhone,
        latitude: address.latitude,
        longitude: address.longitude,
        addressId: address.addressId,
        fromShop: false
      })
      wx.navigateBack({
        delta: 1
      })
    }
  },
  //到地址编辑界面
  edit:function(event){
    var index = event.currentTarget.dataset.index
    var info = JSON.stringify(this.data.list[index]);
    wx.navigateTo({
      url: '/pages/editAddress/editAddress?info='+info,
    })
  },
  //新增地址
  addAddress:function(event){
    wx.navigateTo({
      url: '/pages/editAddress/editAddress'
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
    var addressList = [];
    //获取当前openid收货地址
    wx.request({
      url: app.globalData.host+'/buyer/address/list',
      data: {
        sessionId: app.globalData.sessionId
      },
      success: function (res) {
        var list = res.data.data;
        var i = 0;
        if (list.length>0){
          that.setData({
            list:list
          })
        }else{
          that.setData({
            list: [],
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
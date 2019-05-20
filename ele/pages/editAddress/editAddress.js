// pages/editAddress/editAddress.js
var app =getApp();
var QQMapWX = require('../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isAdd:false,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 实例化API核心类
    qqmapsdk = new QQMapWX({
      key: 'PXSBZ-PB3LW-4FCRX-OFJXZ-IDLT5-X4FSD'
    });

    var that = this;
    wx.getStorage({
      key: 'sessionId',
      success: function (res) {
        that.setData({
          sessionId: res.data
        })
      },
    })
    //更新
    if(options.info!=null){
      var info = JSON.parse(options.info);
      this.setData({
        addressId: info.addressId,
        latitude: info.latitude,
        longitude: info.longitude,
        location: info.location,
        detail: info.detail,
        buyerName: info.buyerName,
        buyerPhone: info.buyerPhone,
        sessionId:app.globalData.sessionId
      })
    }else{
      //新增地址
      this.setData({
        isAdd:true,
        addressId: '',
        latitude: '',
        longitude: '',
        location: '',
        detail: '',
        buyerName: '',
        buyerPhone: '',
        sessionId:app.globalData.sessionId
      })
    }
  },
  //跳转到地址选择界面
  choose:function(event){
    app.globalData.fromEditAddress = 1
    wx.navigateTo({
      url: '/pages/address/address?latitude=' + this.data.latitude + '&longitude=' + this.data.longitude + '&currentLocation=' + this.data.location,
    })
  },
  //input输入事件，动态设置地址
  bindinput:function(event){
    switch (event.currentTarget.dataset.inputType){
      case "buyerName":
        this.data.buyerName = event.detail.value;
        break;
      case "buyerPhone":
        this.data.buyerPhone = event.detail.value;
        break;
      case "detail":
        this.data.detail = event.detail.value;
        break;
    }
  },
  //保存地址
  save:function(event){
    if (this.data.location == "" || this.data.buyerName == "" || this.data.buyerPhone == ""){
      wx.showToast({
        title: '信息不完整',
        icon:'none'
      })
      return;
    }
    if (!(/^1[34578]\d{9}$/.test(this.data.buyerPhone))) {
      wx.showToast({
        title: '手机号格式错误',
        icon: 'none'
      })
      return;
    } 
    wx.request({
      url: app.globalData.host +'/buyer/address/save',
      method:'POST',
      data:{
          addressId: this.data.addressId,
          sessionId: app.globalData.sessionId,
          latitude: this.data.latitude,
          longitude: this.data.longitude,
          location:this.data.location,
          detail: this.data.detail,
          buyerName: this.data.buyerName,
          buyerPhone: this.data.buyerPhone,
      },
      success:function(res){
        if(res.data.code==0){
          wx.showToast({
            title: '成功',
          })
          wx.navigateBack({
            delta: 1
          }) 
        }
      }
    })
  },
  //删除地址
  remove:function(event){
    wx.request({
      url: app.globalData.host +'/buyer/address/remove',
      data:{
        addressId: this.data.addressId,
      },
      success:function(res){
        if (res.data.code == 0) {
          wx.showToast({
            title: '成功',
          })
          wx.navigateBack({
            delta: 1
          }) 
        }
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
    app.globalData.fromEditAddress = 0
    
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
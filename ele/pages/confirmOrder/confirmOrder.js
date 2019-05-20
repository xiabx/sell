// pages/order/order.js
var QQMapWX = require('../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    latitude: '',
    longitude: '',
    addressId: '',
    note:'',
    noAddress:true,
    fromShop:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var cart = app.globalData.inCart;
    var other = JSON.parse(options.other);
    //amount：已优惠
    other.amount = parseFloat(new Number(other.amount + other.distributionFee).toFixed(2));
    for(var i=0;i<cart.length;i++){
      cart[i].amountPrice = parseFloat(new Number(cart[i].amountPrice).toFixed(2));
    }
    this.setData({
      cart:cart,
      other:other
    })
  },
  toNote:function(){
    wx.navigateTo({
      url: '/pages/note/note',
    })
  },
  //前往地址选择界面
  chooseAddress:function(event){
    wx.navigateTo({
      url: '/pages/buyerAddress/buyerAddress?fromMy=0',
    })
  },
  /**创建订单，并支付 */
  toPay:function(){
    wx.showLoading({
      title: '订单创建中',
      mask:true
    })
    //创建订单
    var items=[];
    var cart = this.data.cart;
    var that = this;
    for(var i=0;i<cart.length;i++){
      items.push({
        'productId': cart[i].foodId,
        'productQuantity': cart[i].num
      });
    }
    var parm = {
      addressId: this.data.addressId,
      sessionId: app.globalData.sessionId,
      note: this.data.note,
      sellerId:this.data.other.sellerId,
      items:items
    }
    wx.request({
      url: app.globalData.host +'/buyer/order/create',
      method:'POST',
      data:parm,
      success:function(res){
        var pages = getCurrentPages();
        var prevPage = pages[pages.length - 2];   //上一个页面
        prevPage.setData({
          
        });
        //订单创建成功
        if(res.data.code==0){
          wx.hideLoading();
          wx.redirectTo({
            url: '/pages/pay/pay?orderId='+res.data.data.orderId+'&shopName='+that.data.other.shopName,
          })
        }else{
          wx.hideLoading();
        }
        if(res.data.code == 17){
          wx.showModal({
            title: '提示',
            content: '超出配送范围',
            showCancel:false
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
    var that = this;
    //默认获取第一条地址为默认收货地址
    //获取当前openid收货地址
    if (that.data.latitude == ''){
      wx.request({
        url: app.globalData.host +'/buyer/address/list',
        data: {
          sessionId: app.globalData.sessionId
        },
        success: function (res) {
          var list = res.data.data;
          //该用户存在收货地址
          if (list.length > 0) {
            var address = res.data.data[0];
            that.setData({
              noAddress: false,
              addressId: address.addressId,
              latitude: address.latitude,
              longitude: address.longitude,
              location: address.location,
              detail: address.detail,
              buyerName: address.buyerName,
              buyerPhone: address.buyerPhone,
            })
          } 
        }
      })
    }
    this.setData({
      fromShop:true
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
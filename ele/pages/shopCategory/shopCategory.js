// pages/shopCategory/shopCategory.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // 1综合排序 2销量 3距离
    flag:1,
    block:false
  },
  //改变排序规则
  changeSort:function(e){
    // console.log(e)
    var that = this;
    if (e.currentTarget.dataset.flag==1){
      wx.request({
        url: app.globalData.host +'/buyer/shop/listDefault',
        data: {
          longitude: this.data.longitude,
          latitude: this.data.latitude,
          category: this.data.category
        },
        success(res) {
          that.setData({
            flag: 1,
            shopList: res.data.data
          })
        }
      })
    } else if (e.currentTarget.dataset.flag == 2){
      wx.request({
        url: app.globalData.host +'/buyer/shop/listOrderSold',
        data:{
          longitude: this.data.longitude,
          latitude: this.data.latitude,
          category:this.data.category
        },
        success(res){
          that.setData({
            flag:2,
            shopList: res.data.data
          })
        }
      })
    }else{
      wx.request({
        url: app.globalData.host +'/buyer/shop/listOrderDistance',
        data: {
          longitude: this.data.longitude,
          latitude: this.data.latitude,
          category: this.data.category
        },
        success(res) {
          that.setData({
            flag: 3,
            shopList: res.data.data
          })
        }
      })
    }
  },
  toProducts:function(e){
    var id = e.currentTarget.dataset.id;
    wx.request({
      url: app.globalData.host +'/buyer/shop/simpleDetail',
      data: {
        sellerId: id
      },
      success: function (res) {
        app.globalData.shopIcon = res.data.shopIcon;
        res.data.shopIcon = "";
        var data = JSON.stringify(res.data);
        wx.navigateTo({
          url: '/pages/products/products?sellerInfo=' + data,
        })
      }
    })

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    this.setData({
      longitude:options.longitude,
      latitude:options.latitude,
      category:options.category
    })
    wx.request({
      url: app.globalData.host +'/buyer/shop/listDefault',
      data:{
        longitude: options.longitude,
        latitude: options.latitude,
        category: options.category
      },
      success:function(res){
        if (res.data.data[0]==null){
          that.setData({
            shopList: res.data.data,
            block:true
          })
        }else{
          that.setData({
            shopList: res.data.data,
          })
        }  
      }
    })
    wx.setNavigationBarTitle({
      title: options.categoryname,
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
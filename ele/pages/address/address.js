// pages/address/address.js
var QQMapWX = require('../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    locations:[],
    currentLocation:{},
    currentLocationPois:[],
    doSearch:false,
  },
//输入时触发事件
  bindinput:function(event){
    var var1 = [];
    if (event.detail.value == ''){
      this.setData({
        location: var1,
        keyword: event.detail.value,
        doSearch:false
      })
    }
    var that=this;
    qqmapsdk.getSuggestion({
      keyword: event.detail.value,
      success:function(res){
        for(var i=0;i<res.data.length;i++){
          var1[i]={
            title: res.data[i].title,
            address: res.data[i].address,
            latitude: res.data[i].location.lat,
            longitude: res.data[i].location.lng
          }
        }
        that.setData({
          locations:var1,
          keyword: event.detail.value,
          doSearch:true
        })
      }
    })
  },
  //返回主界面
  back:function(event){
    var that = this;
    //不是从更改地址界面进入
    if (app.globalData.fromEditAddress==0){
      var index = event.currentTarget.dataset.index;
      if(this.data.doSearch==false){
        app.globalData.latitude = this.data.currentLocationPois[index].latitude;
        app.globalData.longitude = this.data.currentLocationPois[index].longitude;
        app.globalData.locationOnShop = this.data.currentLocationPois[index].title;
        
        wx.switchTab({
          url: '/pages/shop/shop',
        });
      }else{
        app.globalData.latitude = this.data.locations[index].latitude;
        app.globalData.longitude = this.data.locations[index].longitude;
        app.globalData.locationOnShop = this.data.locations[index].title;
        wx.switchTab({
          url: '/pages/shop/shop',
        });
      }
    }else{
        var index = event.currentTarget.dataset.index;
        if (this.data.doSearch == false) {
          //获取地址信息
          var location='';
          qqmapsdk.reverseGeocoder({
          location: {
            latitude: this.data.currentLocationPois[index].latitude,
            longitude: this.data.currentLocationPois[index].longitude
          },
          get_poi: 1,
          success: function (res) {
            // location = res.result.pois[0].title;
            var pages = getCurrentPages();
            var prevPage = pages[pages.length - 2];   //上一个页面
            prevPage.setData({
              latitude: that.data.currentLocationPois[index].latitude,
              longitude: that.data.currentLocationPois[index].longitude,
              location: that.data.currentLocationPois[index].title
            })
            wx.navigateBack({
              delta: 1
            })
          }
        })
      } else {
          //获取地址信息
          var location = '';
          qqmapsdk.reverseGeocoder({
            location: {
              latitude: this.data.locations[index].latitude,
              longitude: this.data.locations[index].longitude
            },
            get_poi: 1,
            success: function (res) {
              location = res.result.pois[0].title;
              var pages = getCurrentPages();
              var currPage = pages[pages.length - 1];   //当前页面
              var prevPage = pages[pages.length - 2];   //上一个页面
              prevPage.setData({
                latitude: that.data.locations[index].latitude,
                longitude: that.data.locations[index].longitude,
                location: that.data.locations[index].title
              })
              wx.navigateBack({
                delta: 1
              })
            }
          })
      }
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    //从添加收货地址进来的
    if (options.latitude==''){
      this.setData({
        doSearch:true,
      })
    }else{
      this.setData({
        currentLocation: options.currentLocation,
      })
    }
    // 实例化API核心类
    qqmapsdk = new QQMapWX({
      key: 'PXSBZ-PB3LW-4FCRX-OFJXZ-IDLT5-X4FSD'
    });
    //获取周围位置
    var that = this;
    qqmapsdk.reverseGeocoder({
      location: {
        latitude: options.latitude,
        longitude: options.longitude
      },
      get_poi:1,
      success:function(res){
        var var1=[];
        var var2={};
        var2={
          title: res.result.pois[0].title,
          address:res.result.address,
          latitude: res.result.location.lat,
          longitude: res.result.location.lng
        }
        for(var i=0;i<res.result.pois.length;i++){
          var1[i]={
            title: res.result.pois[i].title,
            address: res.result.pois[i].address,
            latitude: res.result.pois[i].location.lat,
            longitude: res.result.pois[i].location.lng
          }
        }
        that.setData({
          currentLocationPois:var1
        })
      },
      fail: function (res) {
        console.log(res)
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
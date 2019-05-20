// pages/shop/shop.js
var QQMapWX = require('../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;
var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    category1:[
        {
          image:'/images/category/1.png', 
          text:'美食', 
          categoryType:'c1'
        }, 
        {
          image: '/images/category/2.png',
          text: '超市', 
          categoryType: 'c2'
        },
        {
          image: '/images/category/3.png',
          text: '生鲜果蔬', 
          categoryType: 'c3'
        },
        {
          image: '/images/category/4.png',
          text: '甜点饮品', 
          categoryType: 'c4'
        },
      ],
      category2:[
        {
          image: '/images/category/5.png',
          text: '精品推荐',
          categoryType: 'c5'
        },
        {
          image: '/images/category/6.png',
          text: '特快专送',
          categoryType: 'c6'
        },
        {
          image: '/images/category/7.png',
          text: '小吃馆',
          categoryType: 'c7'
        },
        {
          image: '/images/category/8.png',
          text: '海鲜',
          categoryType: 'c8'
        }
      ],
    latitude:'11',
    longitude:'11',
    location:'',
    shops:[],
    hello:'hello',
    flag: 1,
    noShop: false,

  },
  toCategory:function(e){
    wx.navigateTo({
      url: '/pages/shopCategory/shopCategory?longitude=' + this.data.longitude + '&latitude=' + this.data.latitude + '&category=' + e.currentTarget.dataset.categorytype + '&categoryname=' + e.currentTarget.dataset.categoryname,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    //强制授权
    wx.getSetting({
      success: function (res) {

        wx.authorize({
          scope: "scope.userLocation",
          success: function () {
            // console.log("ok")
          }
        })
        //没给位置 信息 授权
        if (!res.authSetting['scope.userLocation'] || !res.authSetting['scope.userInfo']) {
          wx.showModal({
            title: '授权提示',
            content: '为提供更好体验，请先授权',
            showCancel:false,
            success:function(res){
              if(res.confirm == true){
                wx.redirectTo({
                  url: '/pages/auth/auth',
                })
              }
            }
          })
        }else{
          wx.getUserInfo({
            success: res => {
              app.globalData.userInfo = res.userInfo
              app.globalData.hasUserInfo = true
            }
          })
        }
      }
    })
    //登陆
    wx.checkSession({
      success:function(res){
        //  console.log('你的session没过期')
        var sessionId = wx.getStorageSync('sessionId');
        wx.request({
          url: app.globalData.host +'/buyer/checkSessionExist',
          data:{
            sessionId:sessionId
          },
          success:function(res){
            // console.log(res);
            if (res.data.data == 'noExist'){
              wx.login({
                success: function (res) {
                  wx.request({
                    url: app.globalData.host + '/buyer/WXLogin',
                    data: {
                      code: res.code
                    },
                    success: function (res) {
                      app.globalData.sessionId = res.data;
                      wx.setStorage({
                        key: 'sessionId',
                        data: res.data,
                      })
                    }
                  });
                }
              })
            }
          }
        })
      },
      fail:function(res){
        wx.login({
          success: function (res) {
            wx.request({
              url: app.globalData.host +'/buyer/WXLogin',
              data: {
                code: res.code
              },
              success:function(res){
                app.globalData.sessionId = res.data;
                wx.setStorage({
                  key: 'sessionId',
                  data: res.data,
                })
              }
            });
          }
        })
      }
    })
  },
  //前往地址选择界面
  toAddress:function(){
    wx.navigateTo({
      url: '/pages/address/address?latitude=' + this.data.latitude +'&longitude='+this.data.longitude+'&currentLocation='+this.data.location,
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },
  //前往商品详情页
  toGoods:function(event){
    var id = event.currentTarget.dataset.id;
    wx.request({
      url: app.globalData.host +'/buyer/shop/simpleDetail',
      data:{
        sellerId:id
      },
      success:function(res){
        app.globalData.shopIcon = res.data.shopIcon;
        res.data.shopIcon='';
        var data = JSON.stringify(res.data);
        wx.navigateTo({
          url: '/pages/products/products?sellerInfo=' + data,
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this
    // 实例化API核心类
    qqmapsdk = new QQMapWX({
      key: 'PXSBZ-PB3LW-4FCRX-OFJXZ-IDLT5-X4FSD'
    });
    //当程序第一次启动时，获取经纬度
    if (app.globalData.latitude == '' && app.globalData.longitude == '') {
      wx.getLocation({
        type: "gcj02",
        success: function (res) {
          //根据小程序经纬度获取位置信息
          qqmapsdk.reverseGeocoder({
            location: {
              latitude: res.latitude,
              longitude: res.longitude
            },
            get_poi: 1,
            success: function (res) {
              app.globalData.locationOnShop = res.result.pois[0].title;
              that.setData({
                location: res.result.pois[0].title
              })
            }
          });
          app.globalData.longitude = res.longitude;
          app.globalData.latitude = res.latitude;
          that.setData({
            latitude: res.latitude,
            longitude: res.longitude
          })
          wx.request({
            url: app.globalData.host +'/buyer/shop/list',
            data: {
              longitude: that.data.longitude,
              latitude: that.data.latitude,
            },
            success: function (res) {
              for(var i = 0;i<res.data.data.length;i++){
                res.data.data[i].distance = res.data.data[i].distance.toFixed(0);
              }
              that.setData({
                shops: res.data.data
              })
            }
          })
        },
      });
    } else {
      qqmapsdk.reverseGeocoder({
        location: {
          latitude: app.globalData.latitude,
          longitude: app.globalData.longitude
        },
        get_poi: 1,
        success: function (res) {
          that.setData({
            location: app.globalData.locationOnShop,
            latitude: app.globalData.latitude,
            longitude: app.globalData.longitude
          })
          wx.request({
            url: app.globalData.host +'/buyer/shop/list',
            data: {
              longitude: that.data.longitude,
              latitude: that.data.latitude,
            },
            success: function (res) {
              if (res.data.data[0] == null) {
                for (var i = 0; i < res.data.data.length; i++) {
                  res.data.data[i].distance = res.data.data[i].distance.toFixed(0);
                }
                that.setData({
                  noShop: true,
                  shops: res.data.data
                })
              }else{
                for (var i = 0; i < res.data.data.length; i++) {
                  res.data.data[i].distance = res.data.data[i].distance.toFixed(0);
                }
                that.setData({
                  noShop: false,
                  shops: res.data.data
                })
              }
            }
          })
        }
      });
    }; 
  },
  //改变排序规则
  changeSort: function (e) {
    // console.log(e)
    var that = this;
    if (e.currentTarget.dataset.flag == 1) {
      wx.request({
        url: app.globalData.host +'/buyer/shop/list',
        data: {
          longitude: this.data.longitude,
          latitude: this.data.latitude
        },
        success(res) {
          for (var i = 0; i < res.data.data.length; i++) {
            res.data.data[i].distance = res.data.data[i].distance.toFixed(0);
          }
          that.setData({
            flag: 1,
            shops: res.data.data
          })
        }
      })
    } else if (e.currentTarget.dataset.flag == 2) {
      wx.request({
        url: app.globalData.host +'/buyer/shop/listSold',
        data: {
          longitude: this.data.longitude,
          latitude: this.data.latitude
        },
        success(res) {
          for (var i = 0; i < res.data.data.length; i++) {
            res.data.data[i].distance = res.data.data[i].distance.toFixed(0);
          }
          that.setData({
            flag: 2,
            shops: res.data.data
          })
        }
      })
    } else {
      wx.request({
        url: app.globalData.host +'/buyer/shop/listDistance',
        data: {
          longitude: this.data.longitude,
          latitude: this.data.latitude
        },
        success(res) {
          for (var i = 0; i < res.data.data.length; i++) {
            res.data.data[i].distance = res.data.data[i].distance.toFixed(0);
          }
          that.setData({
            flag: 3,
            shops: res.data.data
          })
        }
      })
    }
  },
  //授权页面回调
  locationAuth:function(){
    var that = this;
    wx.getSetting({
      success:function(res){
        if (res.authSetting['scope.userLocation']) {
          that.setData({
            hasLocationAuth:true,
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
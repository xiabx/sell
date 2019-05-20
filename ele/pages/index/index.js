//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
  },
  toLogin:function(){
    wx.login({
      success:function(res){
        wx.request({
          url: app.globalData.host +'/buyer/WXLogin',
          data:{
            code:res.code
          }
        });
        wx.getUserInfo({
          success:function(userRes){
            wx.request({
              url: app.globalData.host +'/buyer/checkRawData',
              data: {
                rawData: userRes.rawData,
                signature:userRes.signature
              },
              method: 'GET',
              success: function (res) {
                console.log(res.data);
              }
            })
          }
        })
      }
    })
  },
  checkSession:function(){
    wx.checkSession({
      success:function(){
        console.log('session未过期');
      },
      fail:function(){
        console.log('session过期');
      }
    })
  }
})

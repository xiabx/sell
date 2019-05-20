var app = getApp();
Page({
  data: {
    flag: 0,
    noteMaxLen: 300, // 最多放多少字
    info: "",
    noteNowLen: 0,//备注当前字数
  },
  // 监听字数
  bindTextAreaChange: function (e) {
    var that = this
    var value = e.detail.value,
      len = parseInt(value.length);
    if (len > that.data.noteMaxLen)
      return;
    that.setData({ info: value, noteNowLen: len })
  },
  // 提交清空当前值
  bindSubmit: function () {
    wx.showLoading({
      title: '发布评价中',
    })
    var that = this;
    wx.request({
      url: 'http://localhost:8080/buyer/comment/add',
      method: 'POST',
      data:{
        orderId:this.data.orderId,
        sessionId:app.globalData.sessionId,
        content:this.data.info,
        star:this.data.flag,
        buyerName: app.globalData.userInfo.userInfo.nickName,
        buyerIcon: app.globalData.userInfo.userInfo.avatarUrl
      },
      success:function(res){
        wx.hideLoading();
        if(res.data.data == true){
          wx.showToast({
            title: '发布成功',
            icon: 'success',
            duration: 1500,
            mask: false,
            success: function () {
              that.setData({ info: '', noteNowLen: 0, flag: 0 })
            }
          })
          wx.navigateBack({
            delta:1
          })
        }
      }
    })

   

  },
  changeColor1: function () {
    var that = this;
    that.setData({
      flag: 1
    });
  },
  changeColor2: function () {
    var that = this;
    that.setData({
      flag: 2
    });
  },
  changeColor3: function () {
    var that = this;
    that.setData({
      flag: 3
    });
  },
  changeColor4: function () {
    var that = this;
    that.setData({
      flag: 4
    });
  },
  changeColor5: function () {
    var that = this;
    that.setData({
      flag: 5
    });
  },

  onLoad:function(options){
    this.setData({
      orderId:options.orderId
    })
  }

})

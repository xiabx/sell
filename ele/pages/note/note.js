var app = getApp();
Page({
  data: {
    note:'',
    noteMaxLen: 50, // 最多放多少字
    noteNowLen: 0,//备注当前字数
  },
  // 监听字数
  bindTextAreaChange: function (e) {
    var that = this
    var value = e.detail.value,
      len = parseInt(value.length);
    if (len > that.data.noteMaxLen)
      return;
    that.setData({ note: value, noteNowLen: len })
  },
  // 提交清空当前值
  bindSubmit: function () {
    var pages = getCurrentPages();
    var prevPage = pages[pages.length - 2];   //上一个页面
    prevPage.setData({
      note:this.data.note
    })
    wx.navigateBack({
      delta:1
    })
  },
  onLoad: function (options) {

  }
})

// pages/meitaun/meituan.js
var QQMapWX = require('../../lib/qqmap-wx-jssdk.js');
var qqmapsdk;
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    shopName: '',
    toView: '',
    currentType: '',
    cart: [],
    cartNum: 0,
    amount: 0,
    showAmount: 0,
    showCart: false,
    list: {},
    currentDiscount: {},
    typeindex:0,
    showView:{},
    commentPage:1,
    commentIsLastPage: false,
    comments:[],
    onComment:false,
    showTab:1,//显示的tab 1 点餐 2 评论 3 商家
  },
  /**设置标签跳越 */
  toClassification: function(e) {
    var typeindex = e.currentTarget.dataset.typeindex;
    this.setData({
      showView:this.data.list.data[typeindex],
      typeindex: typeindex
    })
  },
  //切换tab
  changeTab:function(event){
    this.setData({
      showTab: event.currentTarget.dataset.type
    })
  },

  /**往购物车里加商品*/
  addFood: function(e) {
    var foodId = e.currentTarget.dataset.foodId;
    var list = this.data.list;
    var foodList = list.data;
    var cart = this.data.cart;
    var cartNum = this.data.cartNum;
    var amount = this.data.amount;
    var showView = this.data.showView;
    //在购物车中
    for (var k = 0; k < cart.length; k++) {
      //更新购物车中该商品的数据
      if (cart[k].foodId == foodId) {
        cart[k].num++;
        cart[k].amountPrice += Math.round(cart[k].price * 10) / 10;
        this.setData({
          cart: cart
        })
        //在list中找到该商品
        for (var i = 0; i < foodList.length; i++) {
          for (var j = 0; j < foodList[i].foods.length; j++) {
            if (foodList[i].foods[j].id == foodId) {
              //更新list中该商品shuju
              foodList[i].foods[j].cartCount++;
              amount += foodList[i].foods[j].price;
              var showAmount = amount;
              //寻找满减信息
              for (var k = 0; k < this.data.discounts.length; k++) {
                if (showAmount > this.data.discounts[k].full) {
                  this.setData({
                    currentDiscount: this.data.discounts[k],
                    haveDiscount: true
                  })
                }
              }
              if (this.data.currentDiscount.full != null) {
                showAmount -= this.data.currentDiscount.reduce;
                showAmount = parseFloat(new Number(showAmount).toFixed(2));
              }
              amount = parseFloat(new Number(amount).toFixed(2));
              break;
            }
          }
        }
        showView = this.data.list.data[this.data.typeindex];
        list.data = foodList;
        cartNum++;
        this.setData({
          cartNum: cartNum,
          list: list,
          amount: amount,
          showAmount: showAmount,
          showView:showView
        })
        return;
      }
    }
    //不在购物车中
    for (var i = 0; i < foodList.length; i++) {
      for (var j = 0; j < foodList[i].foods.length; j++) {
        if (foodList[i].foods[j].id == foodId) {
          var cartContent = {
            foodId: foodId,
            foodName: foodList[i].foods[j].name,
            foodIcon: foodList[i].foods[j].icon,
            num: 1,
            price: foodList[i].foods[j].price,
            amountPrice: foodList[i].foods[j].price
          };
          foodList[i].foods[j].inCart = true;
          foodList[i].foods[j].cartCount = 1;
          cart.push(cartContent);
          amount += foodList[i].foods[j].price;
          var showAmount = amount;
          //寻找满减信息
          for (var k = 0; k < this.data.discounts.length; k++) {
            if (showAmount > this.data.discounts[k].full) {
              this.setData({
                currentDiscount: this.data.discounts[k],
                haveDiscount: true
              })
            }
          }
          if (this.data.currentDiscount.full != null) {
            showAmount -= this.data.currentDiscount.reduce;
            showAmount = parseFloat(new Number(showAmount).toFixed(2));
          }

          amount = parseFloat(new Number(amount).toFixed(2));
          this.setData({
            cart: cart,
            amount: amount,
            showAmount: showAmount
          })
          break;
        }
      }
    }
    showView = this.data.list.data[this.data.typeindex];
    list.data = foodList;
    cartNum++;
    this.setData({
      list: list,
      cartNum: cartNum,
      showView: showView
    })
  },

  /**从购物车里减商品*/
  subFood: function(e) {
    var foodId = e.currentTarget.dataset.foodId;
    var list = this.data.list;
    var foodList = list.data;
    var cart = this.data.cart;
    var cartNum = this.data.cartNum;
    var amount = this.data.amount;
    var showView = this.data.showView;
    //在购物车中找到该商品  k
    for (var k = 0; k < cart.length; k++) {
      if (cart[k].foodId == foodId) {
        //当购物车中该商品只剩最后一个
        if (cart[k].num == 1) {
          amount -= cart[k].price;
          var showAmount = amount;
          //寻找满减信息
          //没达到满减最低条件
          if (showAmount < this.data.discounts[0].full) {
            this.setData({
              currentDiscount: {},
              haveDiscount: false
            })
          } else {
            for (var z = 0; z < this.data.discounts.length; z++) {
              if (showAmount >= this.data.discounts[z].full) {
                this.setData({
                  currentDiscount: this.data.discounts[z],
                  haveDiscount: true
                })
              }
            }
          }
          if (this.data.currentDiscount.full != null) {
            showAmount -= this.data.currentDiscount.reduce;
            showAmount = parseFloat(new Number(showAmount).toFixed(2));
          }
          amount = parseFloat(new Number(amount).toFixed(2));
          cart.splice(k, 1);
          for (var i = 0; i < foodList.length; i++) {
            for (var j = 0; j < foodList[i].foods.length; j++) {
              if (foodList[i].foods[j].id == foodId) {
                foodList[i].foods[j].cartCount = 0;
                foodList[i].foods[j].inCart = false;
              }
            }
          }
        } else {
          cart[k].num--;
          cart[k].amountPrice -= cart[k].price;
          for (var i = 0; i < foodList.length; i++) {
            for (var j = 0; j < foodList[i].foods.length; j++) {
              if (foodList[i].foods[j].id == foodId) {
                foodList[i].foods[j].cartCount--;
                amount -= foodList[i].foods[j].price;
                var showAmount = amount;
                //寻找满减信息
                //没达到满减最低条件
                if (showAmount < this.data.discounts[0].full) {
                  this.setData({
                    currentDiscount: {},
                    haveDiscount: false
                  })
                } else {
                  for (var k = 0; k < this.data.discounts.length; k++) {
                    if (showAmount >= this.data.discounts[k].full) {
                      this.setData({
                        currentDiscount: this.data.discounts[k],
                        haveDiscount: true
                      })
                    }
                  }
                }
                if (this.data.currentDiscount.full != null) {
                  showAmount -= this.data.currentDiscount.reduce;
                  showAmount = parseFloat(new Number(showAmount).toFixed(2));
                }
                amount = parseFloat(new Number(amount).toFixed(2));
              }
            }
          }
        }
        var showCart = this.data.showCart;
        if (cart[0] == null) {
          showCart = false;
        }
        showView = this.data.list.data[this.data.typeindex];
        cartNum--;
        this.setData({
          amount: amount,
          cart: cart,
          list: list,
          cartNum: cartNum,
          showCart: showCart,
          showAmount: showAmount,
          showView:showView
        })
      }
    }
  },
  /**显示购物车*/
  showCart: function() {
    var showCart = this.data.showCart;
    this.setData({
      showCart: !showCart
    })
  },
  /**清空购物车 */
  cleanCart: function() {
    var list = this.data.list;
    var foodList = list.data;
    var showView=this.data.showView;

    //将所有商品的inCart，cartCount重置
    for (var i = 0; i < foodList.length; i++) {
      for (var j = 0; j < foodList[i].foods.length; j++) {
        foodList[i].foods[j].inCart = false;
        foodList[i].foods[j].cartCount = 0;
      }
    }
    showView = this.data.list.data[this.data.typeindex];
    this.setData({
      cart: [],
      showCart: false,
      cartNum: 0,
      amount: 0,
      list: list,
      currentDiscount: {},
      haveDiscount:false,
      showView:showView
    })
  },
  /**生成订单 */
  createOrder: function() {
    var other = {
      amount: this.data.showAmount,
      discount: this.data.currentDiscount.reduce,
      shopName: this.data.info.shopName,
      distributionFee:this.data.info.distributionFee,
      sellerId:this.data.info.id
    }
    app.globalData.inCart = this.data.cart;
    wx.navigateTo({
      url: '../confirmOrder/confirmOrder?other=' + JSON.stringify(other),
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.showNavigationBarLoading({});
    qqmapsdk = new QQMapWX({
      key: 'PXSBZ-PB3LW-4FCRX-OFJXZ-IDLT5-X4FSD'
    });
    var that = this;
    var sellerInfo = JSON.parse(options.sellerInfo);
    sellerInfo.shopIcon = app.globalData.shopIcon;
    this.setData({
      info: sellerInfo
    })
    wx.setNavigationBarTitle({
      title: this.data.info.shopName,
    })
    //拿到商品列表
    wx.request({
      url: app.globalData.host +'/buyer/product/list',
      data: {
        sellerId: sellerInfo.id
      },
      success: function(res) {
        that.setData({
          list: res.data,
          showView:res.data.data[0]
        })
      }
    })
    //拿到满减信息
    wx.request({
      url: app.globalData.host +'/buyer/shop/discount',
      data: {
        sellerId: sellerInfo.id
      },
      success: function(res) {
        var fullReduceInfo = "";
        for (var i = 0; i < res.data.data.length;i++){
          fullReduceInfo = fullReduceInfo + "满" + res.data.data[i].full + "减" + res.data.data[i].reduce+" "
        }
        that.setData({
          discounts: res.data.data,
          fullReduceInfo: fullReduceInfo
        })
      }
    })

    //拿到评价信息
    wx.request({
      url: app.globalData.host +'/buyer/comment/list',
      data:{
        sellerId:this.data.info.id,
        page:1
      },
      success:function(res){
        if (res.data.data.list[0] == null){
          that.setData({
            noComment:true
          })
        } else {
          for (var i = 0; i < res.data.data.list.length; i++) {
            res.data.data.list[i].createTime = res.data.data.list[i].createTime.split(' ')[0];
          }
          var newCommentList = that.data.comments.concat(res.data.data.list);
          that.setData({
            comments: newCommentList,
            commentIsLastPage: res.data.data.isLastPage,
            commentPage: 2
          })
        }  
      }
    })
    //获取商家位置
    qqmapsdk.reverseGeocoder({
      location: {
        latitude: this.data.info.latitude,
        longitude: this.data.info.longitude
      },
      success: function (res) {
        that.setData({
          location: res.result.formatted_addresses.recommend
        })
      }
    });
    wx.hideNavigationBarLoading({});
  },
  //评论区滚动条事件
  scrolltolower:function(event){
    if (this.data.commentIsLastPage == false ){
      wx.showNavigationBarLoading();
      var that = this;
      wx.request({
        url: app.globalData.host +'/buyer/comment/list',
        data: {
          sellerId: this.data.info.id,
          page: this.data.commentPage
        },
        success: function (res) {
          for (var i = 0; i < res.data.data.list.length; i++) {
            res.data.data.list[i].createTime = res.data.data.list[i].createTime.split(' ')[0];
          }
          var newCommentList = that.data.comments.concat(res.data.data.list);
          that.setData({
            comments: newCommentList,
            commentPage: that.data.commentPage + 1,
            commentIsLastPage: res.data.data.isLastPage
          })
          wx.hideNavigationBarLoading();
        }

      })
    }
    
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})
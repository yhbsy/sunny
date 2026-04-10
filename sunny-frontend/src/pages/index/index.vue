<template>
  <view class="index-page">
    <view class="header">
      <view class="header-content">
        <view class="logo-area">
          <text class="logo-icon">🌾</text>
          <text class="logo-text">Sunny农产品</text>
        </view>
        <view class="header-right">
          <view class="search-box" @click="goSearch">
            <text class="search-icon">🔍</text>
            <text class="search-placeholder">搜索商品...</text>
          </view>
        </view>
      </view>
    </view>

    <swiper class="banner" indicator-dots indicator-color="rgba(255,255,255,0.5)" indicator-active-color="#fff" autoplay circular>
      <swiper-item v-for="item in bannerList" :key="item.id">
        <image class="banner-image" :src="item.image || defaultBanner" mode="aspectFill" @click="handleBannerClick(item)"></image>
        <view class="banner-overlay">
          <text class="banner-title">{{ item.title || '大山里的好货' }}</text>
        </view>
      </swiper-item>
      <swiper-item v-if="bannerList.length === 0">
        <image class="banner-image" :src="defaultBanner" mode="aspectFill"></image>
        <view class="banner-overlay">
          <text class="banner-title">新鲜农产品 直达您的餐桌</text>
        </view>
      </swiper-item>
    </swiper>

    <view class="quick-nav">
      <view class="nav-item" v-for="item in categoryList" :key="item.id" @click="goToCategory(item.id)">
        <view class="nav-icon" :style="{ background: getCategoryColor(item.code) }">
          <text>{{ getCategoryEmoji(item.code) }}</text>
        </view>
        <text class="nav-name">{{ item.name }}</text>
      </view>
    </view>

    <view class="section product-section">
      <view class="section-header">
        <view class="section-title-area">
          <text class="section-title">🔥 热销商品</text>
          <text class="section-subtitle">精选好货 品质保证</text>
        </view>
        <view class="section-more" @click="goToProductList">
          <text>查看全部</text>
          <text class="arrow">›</text>
        </view>
      </view>
      <scroll-view scroll-x class="product-scroll">
        <view class="product-scroll-inner">
          <view class="product-card" v-for="item in productList" :key="item.id" @click="goToDetail(item.id)">
            <view class="product-image-wrap">
              <image class="product-image" :src="item.image || defaultProduct" mode="aspectFill"></image>
              <view class="product-tag" v-if="item.sales > 50">热卖</view>
            </view>
            <view class="product-content">
              <text class="product-name">{{ item.name }}</text>
              <text class="product-origin">{{ item.origin || '大山村' }}</text>
              <view class="product-bottom">
                <view class="product-price">
                  <text class="price-symbol">¥</text>
                  <text class="price-value">{{ item.price }}</text>
                  <text class="price-unit">/{{ item.unit }}</text>
                </view>
                <view class="add-btn" @click.stop="addToCart(item)">+</view>
              </view>
            </view>
          </view>
        </view>
      </scroll-view>
    </view>

    <view class="section poetry-section">
      <view class="section-header">
        <view class="section-title-area">
          <text class="section-title">📜 诗词天地</text>
          <text class="section-subtitle">山间诗意 田园风光</text>
        </view>
        <view class="section-more" @click="goToPoetryList">
          <text>更多</text>
          <text class="arrow">›</text>
        </view>
      </view>
      <view class="poetry-list">
        <view class="poetry-card" v-for="item in poetryList" :key="item.id" @click="goToPoetry(item.id)">
          <view class="poetry-header">
            <text class="poetry-title">{{ item.title }}</text>
            <text class="poetry-author">{{ item.author }}</text>
          </view>
          <text class="poetry-content">{{ formatPoetry(item.content) }}</text>
          <view class="poetry-footer">
            <text class="poetry-scene" v-if="item.scene">{{ item.scene.substring(0, 20) }}...</text>
          </view>
        </view>
      </view>
    </view>

    <view class="section logistics-section">
      <view class="section-header">
        <view class="section-title-area">
          <text class="section-title">🚚 城乡物流</text>
          <text class="section-subtitle">连接城乡 传递温情</text>
        </view>
      </view>
      <view class="logistics-info">
        <view class="logistics-item">
          <view class="logistics-icon to-city">
            <text>🏙️</text>
          </view>
          <view class="logistics-text">
            <text class="logistics-title">进城</text>
            <text class="logistics-desc">山货进城 新鲜直达</text>
          </view>
        </view>
        <view class="logistics-divider"></view>
        <view class="logistics-item">
          <view class="logistics-icon to-country">
            <text>🏡</text>
          </view>
          <view class="logistics-text">
            <text class="logistics-title">下乡</text>
            <text class="logistics-desc">城货下乡 便利到家</text>
          </view>
        </view>
      </view>
    </view>

    <view class="section contact-section">
      <view class="section-header">
        <view class="section-title-area">
          <text class="section-title">📱 联系我们</text>
          <text class="section-subtitle">关注我们 获取更多优惠</text>
        </view>
      </view>
      <view class="contact-cards">
        <view class="contact-card" @click="copyWechat">
          <view class="contact-icon wechat">
            <image src="/static/images/wechat.png" mode="aspectFit"></image>
          </view>
          <view class="contact-info">
            <text class="contact-label">微信</text>
            <text class="contact-value">{{ config.wechat || 'sunny-farm' }}</text>
          </view>
          <text class="contact-action">复制</text>
        </view>
        <view class="contact-card" @click="copyDouyin">
          <view class="contact-icon douyin">
            <image src="/static/images/douyin.png" mode="aspectFit"></image>
          </view>
          <view class="contact-info">
            <text class="contact-label">抖音</text>
            <text class="contact-value">{{ config.douyin || 'sunny-farm' }}</text>
          </view>
          <text class="contact-action">复制</text>
        </view>
      </view>
    </view>

    <view class="footer">
      <text class="footer-text">© 2024 Sunny农产品 大山里的好货</text>
    </view>
  </view>
</template>

<script>
import api from '../../api/index'

export default {
  data() {
    return {
      bannerList: [],
      categoryList: [],
      productList: [],
      poetryList: [],
      config: {
        wechat: '',
        douyin: ''
      },
      defaultBanner: '/static/images/banner.jpg',
      defaultProduct: '/static/images/product-default.jpg'
    }
  },
  onLoad() {
    this.loadData()
  },
  methods: {
    async loadData() {
      try {
        const [banner, category, product, poetry] = await Promise.all([
          api.getBannerList().catch(() => []),
          api.getCategoryList().catch(() => []),
          api.getProductList().catch(() => []),
          api.getPoetryList().catch(() => [])
        ])
        this.bannerList = banner || []
        this.categoryList = category || []
        this.productList = (product || []).slice(0, 10)
        this.poetryList = (poetry || []).slice(0, 3)
        
        try {
          const wechat = await api.getConfig('wechat')
          const douyin = await api.getConfig('douyin')
          this.config.wechat = wechat || 'sunny-farm'
          this.config.douyin = douyin || 'sunny-farm'
        } catch (e) {}
      } catch (e) {
        console.error(e)
      }
    },
    getCategoryColor(code) {
      const colors = {
        'ganhuo': 'linear-gradient(135deg, #FF6B6B, #FF8E53)',
        'shucai': 'linear-gradient(135deg, #4CAF50, #8BC34A)',
        'shengtaiyou': 'linear-gradient(135deg, #FFC107, #FF9800)',
        'jiu': 'linear-gradient(135deg, #9C27B0, #E91E63)',
        'yangzhi': 'linear-gradient(135deg, #2196F3, #03A9F4)'
      }
      return colors[code] || 'linear-gradient(135deg, #4CAF50, #8BC34A)'
    },
    getCategoryEmoji(code) {
      const emojis = {
        'ganhuo': '🌶️',
        'shucai': '🥬',
        'shengtaiyou': '🫒',
        'jiu': '🍶',
        'yangzhi': '🐔'
      }
      return emojis[code] || '🌾'
    },
    formatPoetry(content) {
      if (!content) return ''
      return content.replace(/\n/g, ' / ').substring(0, 60) + '...'
    },
    handleBannerClick(item) {
      if (item && item.link) {
        uni.navigateTo({ url: item.link })
      }
    },
    goSearch() {
      uni.navigateTo({ url: '/pages/product/list' })
    },
    goToCategory(categoryId) {
      uni.navigateTo({ url: `/pages/product/list?categoryId=${categoryId}` })
    },
    goToProductList() {
      uni.navigateTo({ url: '/pages/product/list' })
    },
    goToDetail(id) {
      uni.navigateTo({ url: `/pages/product/detail?id=${id}` })
    },
    goToPoetryList() {
      uni.navigateTo({ url: '/pages/poetry/list' })
    },
    goToPoetry(id) {
      uni.navigateTo({ url: `/pages/poetry/detail?id=${id}` })
    },
    addToCart(item) {
      const cart = uni.getStorageSync('cart') || []
      const existIndex = cart.findIndex(p => p.id === item.id)
      if (existIndex > -1) {
        cart[existIndex].quantity += 1
      } else {
        cart.push({ ...item, quantity: 1 })
      }
      uni.setStorageSync('cart', cart)
      uni.showToast({ title: '已加入购物车', icon: 'success' })
    },
    copyWechat() {
      uni.setClipboardData({
        data: this.config.wechat || 'sunny-farm',
        success: () => {
          uni.showToast({ title: '微信号已复制', icon: 'success' })
        }
      })
    },
    copyDouyin() {
      uni.setClipboardData({
        data: this.config.douyin || 'sunny-farm',
        success: () => {
          uni.showToast({ title: '抖音号已复制', icon: 'success' })
        }
      })
    }
  }
}
</script>

<style>
page {
  background: #f5f5f5;
}
</style>

<style scoped>
.index-page {
  min-height: 100vh;
  background: #f5f5f5;
  padding-bottom: 120rpx;
}

.header {
  background: linear-gradient(135deg, #4CAF50 0%, #8BC34A 100%);
  padding: 20rpx 24rpx;
  padding-top: calc(20rpx + var(--status-bar-height, 0));
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo-area {
  display: flex;
  align-items: center;
}

.logo-icon {
  font-size: 48rpx;
  margin-right: 12rpx;
}

.logo-text {
  font-size: 36rpx;
  font-weight: bold;
  color: #fff;
}

.header-right {
  flex: 1;
  margin-left: 40rpx;
}

.search-box {
  background: rgba(255, 255, 255, 0.9);
  border-radius: 40rpx;
  padding: 16rpx 24rpx;
  display: flex;
  align-items: center;
}

.search-icon {
  font-size: 28rpx;
  margin-right: 12rpx;
}

.search-placeholder {
  font-size: 26rpx;
  color: #999;
}

.banner {
  width: 100%;
  height: 360rpx;
}

.banner-image {
  width: 100%;
  height: 100%;
}

.banner-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(transparent, rgba(0,0,0,0.6));
  padding: 40rpx 24rpx 24rpx;
}

.banner-title {
  color: #fff;
  font-size: 32rpx;
  font-weight: bold;
}

.quick-nav {
  display: flex;
  background: #fff;
  padding: 30rpx 12rpx;
  margin: 20rpx;
  border-radius: 20rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.05);
}

.nav-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.nav-icon {
  width: 100rpx;
  height: 100rpx;
  border-radius: 24rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 44rpx;
  margin-bottom: 12rpx;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
}

.nav-name {
  font-size: 24rpx;
  color: #333;
  font-weight: 500;
}

.section {
  background: #fff;
  margin: 20rpx;
  border-radius: 20rpx;
  padding: 24rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.05);
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24rpx;
}

.section-title-area {
  display: flex;
  flex-direction: column;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.section-subtitle {
  font-size: 22rpx;
  color: #999;
  margin-top: 4rpx;
}

.section-more {
  display: flex;
  align-items: center;
  font-size: 24rpx;
  color: #4CAF50;
}

.section-more .arrow {
  font-size: 32rpx;
  margin-left: 4rpx;
}

.product-scroll {
  white-space: nowrap;
  margin: 0 -24rpx;
  padding: 0 24rpx;
}

.product-scroll-inner {
  display: inline-flex;
  padding-right: 24rpx;
}

.product-card {
  width: 280rpx;
  background: #fafafa;
  border-radius: 16rpx;
  overflow: hidden;
  margin-right: 20rpx;
  display: inline-block;
  vertical-align: top;
}

.product-image-wrap {
  position: relative;
  width: 100%;
  height: 280rpx;
}

.product-image {
  width: 100%;
  height: 100%;
}

.product-tag {
  position: absolute;
  top: 12rpx;
  left: 12rpx;
  background: linear-gradient(135deg, #FF6B6B, #FF8E53);
  color: #fff;
  font-size: 20rpx;
  padding: 4rpx 12rpx;
  border-radius: 20rpx;
}

.product-content {
  padding: 16rpx;
}

.product-name {
  font-size: 28rpx;
  color: #333;
  font-weight: 500;
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.product-origin {
  font-size: 22rpx;
  color: #999;
  display: block;
  margin-top: 8rpx;
}

.product-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 12rpx;
}

.product-price {
  display: flex;
  align-items: baseline;
}

.price-symbol {
  font-size: 24rpx;
  color: #FF5722;
  font-weight: bold;
}

.price-value {
  font-size: 36rpx;
  color: #FF5722;
  font-weight: bold;
}

.price-unit {
  font-size: 22rpx;
  color: #999;
  margin-left: 4rpx;
}

.add-btn {
  width: 48rpx;
  height: 48rpx;
  background: linear-gradient(135deg, #4CAF50, #8BC34A);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 32rpx;
  font-weight: bold;
}

.poetry-list {
  display: flex;
  flex-direction: column;
  gap: 16rpx;
}

.poetry-card {
  background: linear-gradient(135deg, #f8f9fa, #e9ecef);
  border-radius: 16rpx;
  padding: 20rpx;
}

.poetry-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12rpx;
}

.poetry-title {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
}

.poetry-author {
  font-size: 24rpx;
  color: #4CAF50;
}

.poetry-content {
  font-size: 26rpx;
  color: #666;
  line-height: 1.8;
}

.poetry-footer {
  margin-top: 12rpx;
  padding-top: 12rpx;
  border-top: 1rpx solid rgba(0,0,0,0.05);
}

.poetry-scene {
  font-size: 22rpx;
  color: #999;
}

.logistics-info {
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #e8f5e9, #f1f8e9);
  border-radius: 16rpx;
  padding: 24rpx;
}

.logistics-item {
  flex: 1;
  display: flex;
  align-items: center;
}

.logistics-icon {
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 36rpx;
  margin-right: 16rpx;
}

.logistics-icon.to-city {
  background: linear-gradient(135deg, #2196F3, #03A9F4);
}

.logistics-icon.to-country {
  background: linear-gradient(135deg, #4CAF50, #8BC34A);
}

.logistics-text {
  display: flex;
  flex-direction: column;
}

.logistics-title {
  font-size: 28rpx;
  font-weight: bold;
  color: #333;
}

.logistics-desc {
  font-size: 24rpx;
  color: #666;
  margin-top: 4rpx;
}

.logistics-divider {
  width: 1rpx;
  height: 60rpx;
  background: rgba(0,0,0,0.1);
  margin: 0 20rpx;
}

.contact-cards {
  display: flex;
  gap: 16rpx;
}

.contact-card {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #fafafa;
  border-radius: 16rpx;
  padding: 24rpx;
}

.contact-icon {
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 12rpx;
}

.contact-icon image {
  width: 48rpx;
  height: 48rpx;
}

.contact-icon.wechat {
  background: #07C160;
}

.contact-icon.douyin {
  background: #000;
}

.contact-info {
  text-align: center;
}

.contact-label {
  font-size: 24rpx;
  color: #999;
  display: block;
}

.contact-value {
  font-size: 28rpx;
  color: #333;
  font-weight: 500;
  margin-top: 4rpx;
}

.contact-action {
  font-size: 22rpx;
  color: #4CAF50;
  margin-top: 12rpx;
  padding: 8rpx 24rpx;
  border: 1rpx solid #4CAF50;
  border-radius: 30rpx;
}

.footer {
  text-align: center;
  padding: 40rpx 24rpx;
  background: #fff;
  margin-top: 20rpx;
}

.footer-text {
  font-size: 24rpx;
  color: #999;
}

@media (min-width: 768px) {
  .header {
    padding: 24rpx 80rpx;
  }
  
  .header-content {
    max-width: 1200rpx;
    margin: 0 auto;
  }
  
  .banner {
    height: 500rpx;
  }
  
  .quick-nav {
    max-width: 1200rpx;
    margin: 30rpx auto;
    padding: 40rpx 24rpx;
  }
  
  .nav-icon {
    width: 120rpx;
    height: 120rpx;
    font-size: 56rpx;
  }
  
  .nav-name {
    font-size: 28rpx;
  }
  
  .section {
    max-width: 1200rpx;
    margin: 30rpx auto;
    padding: 32rpx;
  }
  
  .product-card {
    width: 320rpx;
  }
  
  .product-image-wrap {
    height: 320rpx;
  }
  
  .poetry-list {
    flex-direction: row;
    flex-wrap: wrap;
  }
  
  .poetry-card {
    width: calc(50% - 8rpx);
  }
  
  .contact-cards {
    justify-content: center;
  }
  
  .contact-card {
    width: 300rpx;
    flex: none;
  }
}
</style>

<template>
  <view class="category-page">
    <view class="category-tabs">
      <view 
        class="tab-item" 
        :class="{ active: activeCategory === 0 }" 
        @click="selectCategory(0)">
        全部
      </view>
      <view 
        class="tab-item" 
        :class="{ active: activeCategory === item.id }" 
        v-for="item in categoryList" 
        :key="item.id"
        @click="selectCategory(item.id)">
        {{ item.name }}
      </view>
    </view>

    <view class="product-list">
      <view class="product-item" v-for="item in productList" :key="item.id" @click="goToDetail(item.id)">
        <image class="product-image" :src="item.image || '/static/images/default.png'" mode="aspectFill"></image>
        <view class="product-info">
          <text class="product-name">{{ item.name }}</text>
          <text class="product-desc">{{ item.description || '农家好货，品质保证' }}</text>
          <view class="product-bottom">
            <view class="product-price">
              <text class="price">¥{{ item.price }}</text>
              <text class="unit">/{{ item.unit }}</text>
            </view>
            <view class="add-cart" @click.stop="addToCart(item)">
              <text>加入购物车</text>
            </view>
          </view>
        </view>
      </view>
    </view>

    <view class="empty" v-if="productList.length === 0">
      <text>暂无商品</text>
    </view>
  </view>
</template>

<script>
import api from '../../api/index'

export default {
  data() {
    return {
      categoryList: [],
      productList: [],
      activeCategory: 0
    }
  },
  onLoad() {
    this.loadCategory()
    this.loadProduct()
  },
  methods: {
    async loadCategory() {
      try {
        const data = await api.getCategoryList()
        this.categoryList = data || []
      } catch (e) {
        console.error(e)
      }
    },
    async loadProduct() {
      try {
        const data = await api.getProductList()
        this.productList = data || []
      } catch (e) {
        console.error(e)
      }
    },
    async selectCategory(categoryId) {
      this.activeCategory = categoryId
      try {
        if (categoryId === 0) {
          const data = await api.getProductList()
          this.productList = data || []
        } else {
          const data = await api.getProductByCategory(categoryId)
          this.productList = data || []
        }
      } catch (e) {
        console.error(e)
      }
    },
    goToDetail(id) {
      uni.navigateTo({ url: `/pages/product/detail?id=${id}` })
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
    }
  }
}
</script>

<style scoped>
.category-page {
  background: #f8f8f8;
  min-height: 100vh;
}

.category-tabs {
  display: flex;
  flex-wrap: wrap;
  padding: 20rpx;
  background: #fff;
  margin-bottom: 20rpx;
}

.tab-item {
  padding: 16rpx 32rpx;
  margin: 8rpx;
  font-size: 28rpx;
  color: #666;
  background: #f5f5f5;
  border-radius: 30rpx;
}

.tab-item.active {
  background: #4CAF50;
  color: #fff;
}

.product-list {
  padding: 0 20rpx;
}

.product-item {
  display: flex;
  background: #fff;
  border-radius: 16rpx;
  padding: 20rpx;
  margin-bottom: 20rpx;
}

.product-image {
  width: 200rpx;
  height: 200rpx;
  border-radius: 12rpx;
  background: #f5f5f5;
}

.product-info {
  flex: 1;
  padding-left: 20rpx;
  display: flex;
  flex-direction: column;
}

.product-name {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 8rpx;
}

.product-desc {
  font-size: 24rpx;
  color: #999;
  flex: 1;
}

.product-bottom {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.price {
  font-size: 32rpx;
  color: #ff5722;
  font-weight: bold;
}

.unit {
  font-size: 24rpx;
  color: #999;
}

.add-cart {
  background: #4CAF50;
  color: #fff;
  font-size: 24rpx;
  padding: 12rpx 24rpx;
  border-radius: 30rpx;
}

.empty {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
}
</style>

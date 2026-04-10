<template>
  <view class="product-list-page">
    <view class="search-bar">
      <input class="search-input" v-model="keyword" placeholder="搜索商品名称" />
      <view class="search-btn" @click="search">搜索</view>
      <view class="add-btn" @click="goToAdd">添加</view>
    </view>

    <view class="product-list">
      <view class="product-item" v-for="item in productList" :key="item.id">
        <image class="product-image" :src="item.image || '/static/images/default.png'" mode="aspectFill"></image>
        <view class="product-info">
          <text class="product-name">{{ item.name }}</text>
          <text class="product-category">{{ item.categoryName }}</text>
          <view class="product-meta">
            <text class="product-price">¥{{ item.price }}/{{ item.unit }}</text>
            <text class="product-status" :class="{ 'status-off': item.status === 0 }">
              {{ item.status === 1 ? '上架' : '下架' }}
            </text>
          </view>
        </view>
        <view class="product-actions">
          <view class="action-btn edit" @click="goToEdit(item.id)">编辑</view>
          <view class="action-btn delete" @click="handleDelete(item.id)">删除</view>
        </view>
      </view>
    </view>

    <view class="pagination">
      <view class="page-btn" @click="prevPage" :class="{ disabled: pageNum === 1 }">上一页</view>
      <text class="page-info">{{ pageNum }}/{{ totalPages }}</text>
      <view class="page-btn" @click="nextPage" :class="{ disabled: pageNum >= totalPages }">下一页</view>
    </view>
  </view>
</template>

<script>
import api from '../../api/index'

export default {
  data() {
    return {
      productList: [],
      keyword: '',
      pageNum: 1,
      pageSize: 10,
      total: 0,
      totalPages: 1
    }
  },
  onLoad() {
    this.loadProducts()
  },
  methods: {
    async loadProducts() {
      try {
        const data = await api.getProductPage({
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          keyword: this.keyword
        })
        this.productList = data?.records || []
        this.total = data?.total || 0
        this.totalPages = Math.ceil(this.total / this.pageSize) || 1
      } catch (e) {
        console.error(e)
      }
    },
    search() {
      this.pageNum = 1
      this.loadProducts()
    },
    prevPage() {
      if (this.pageNum > 1) {
        this.pageNum--
        this.loadProducts()
      }
    },
    nextPage() {
      if (this.pageNum < this.totalPages) {
        this.pageNum++
        this.loadProducts()
      }
    },
    goToAdd() {
      uni.navigateTo({ url: '/pages/product/edit' })
    },
    goToEdit(id) {
      uni.navigateTo({ url: `/pages/product/edit?id=${id}` })
    },
    handleDelete(id) {
      uni.showModal({
        title: '提示',
        content: '确定删除该商品吗?',
        success: async (res) => {
          if (res.confirm) {
            try {
              await api.deleteProduct(id)
              uni.showToast({ title: '删除成功', icon: 'success' })
              this.loadProducts()
            } catch (e) {
              console.error(e)
            }
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.product-list-page {
  padding: 20rpx;
}

.search-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20rpx;
}

.search-input {
  flex: 1;
  height: 72rpx;
  padding: 0 20rpx;
  border: 1rpx solid #ddd;
  border-radius: 8rpx;
  font-size: 28rpx;
}

.search-btn, .add-btn {
  padding: 0 24rpx;
  height: 72rpx;
  line-height: 72rpx;
  margin-left: 16rpx;
  border-radius: 8rpx;
  font-size: 28rpx;
}

.search-btn {
  background: #1890ff;
  color: #fff;
}

.add-btn {
  background: #52c41a;
  color: #fff;
}

.product-list {
  background: #fff;
  border-radius: 12rpx;
}

.product-item {
  display: flex;
  align-items: center;
  padding: 20rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.product-image {
  width: 120rpx;
  height: 120rpx;
  border-radius: 8rpx;
  background: #f5f5f5;
}

.product-info {
  flex: 1;
  padding: 0 20rpx;
}

.product-name {
  font-size: 30rpx;
  color: #333;
  display: block;
  margin-bottom: 8rpx;
}

.product-category {
  font-size: 24rpx;
  color: #999;
  display: block;
  margin-bottom: 8rpx;
}

.product-meta {
  display: flex;
  align-items: center;
}

.product-price {
  font-size: 28rpx;
  color: #ff4d4f;
  margin-right: 16rpx;
}

.product-status {
  font-size: 24rpx;
  color: #52c41a;
  padding: 4rpx 12rpx;
  background: #f6ffed;
  border-radius: 4rpx;
}

.product-status.status-off {
  color: #999;
  background: #f5f5f5;
}

.product-actions {
  display: flex;
  flex-direction: column;
}

.action-btn {
  padding: 12rpx 24rpx;
  border-radius: 4rpx;
  font-size: 24rpx;
  margin-bottom: 8rpx;
}

.action-btn.edit {
  background: #e6f7ff;
  color: #1890ff;
}

.action-btn.delete {
  background: #fff1f0;
  color: #ff4d4f;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 24rpx;
}

.page-btn {
  padding: 12rpx 24rpx;
  background: #1890ff;
  color: #fff;
  border-radius: 4rpx;
  font-size: 28rpx;
}

.page-btn.disabled {
  background: #ddd;
}

.page-info {
  margin: 0 24rpx;
  font-size: 28rpx;
  color: #666;
}
</style>

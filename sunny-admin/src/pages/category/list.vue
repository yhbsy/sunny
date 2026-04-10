<template>
  <view class="category-list-page">
    <view class="category-list">
      <view class="category-item" v-for="item in categoryList" :key="item.id">
        <view class="category-info">
          <view class="category-icon">{{ item.name.substring(0, 1) }}</view>
          <view class="category-text">
            <text class="category-name">{{ item.name }}</text>
            <text class="category-code">编码: {{ item.code }}</text>
          </view>
        </view>
        <view class="category-status" :class="{ 'status-off': item.status === 0 }">
          {{ item.status === 1 ? '启用' : '禁用' }}
        </view>
      </view>
    </view>

    <view class="empty" v-if="categoryList.length === 0">
      <text>暂无分类数据</text>
    </view>
  </view>
</template>

<script>
import api from '../../api/index'

export default {
  data() {
    return {
      categoryList: []
    }
  },
  onLoad() {
    this.loadCategories()
  },
  methods: {
    async loadCategories() {
      try {
        const data = await api.getCategoryList()
        this.categoryList = data || []
      } catch (e) {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>
.category-list-page {
  padding: 20rpx;
}

.category-list {
  background: #fff;
  border-radius: 12rpx;
}

.category-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 24rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.category-item:last-child {
  border-bottom: none;
}

.category-info {
  display: flex;
  align-items: center;
}

.category-icon {
  width: 80rpx;
  height: 80rpx;
  background: linear-gradient(135deg, #1890ff, #36cfc9);
  border-radius: 12rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 36rpx;
  color: #fff;
  margin-right: 20rpx;
}

.category-text {
  display: flex;
  flex-direction: column;
}

.category-name {
  font-size: 30rpx;
  color: #333;
  margin-bottom: 8rpx;
}

.category-code {
  font-size: 24rpx;
  color: #999;
}

.category-status {
  font-size: 24rpx;
  padding: 8rpx 16rpx;
  border-radius: 4rpx;
  background: #f6ffed;
  color: #52c41a;
}

.category-status.status-off {
  background: #f5f5f5;
  color: #999;
}

.empty {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
}
</style>

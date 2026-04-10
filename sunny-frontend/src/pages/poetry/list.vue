<template>
  <view class="poetry-list-page">
    <view class="poetry-item" v-for="item in poetryList" :key="item.id" @click="goToDetail(item.id)">
      <view class="poetry-header">
        <text class="poetry-title">{{ item.title }}</text>
        <text class="poetry-author">{{ item.author }}</text>
      </view>
      <text class="poetry-content">{{ formatContent(item.content) }}</text>
      <view class="poetry-footer" v-if="item.scene">
        <text class="scene-label">【景物】</text>
        <text class="scene-text">{{ item.scene }}</text>
      </view>
    </view>

    <view class="empty" v-if="poetryList.length === 0">
      <text>暂无诗词</text>
    </view>
  </view>
</template>

<script>
import api from '../../api/index'

export default {
  data() {
    return {
      poetryList: []
    }
  },
  onLoad() {
    this.loadPoetry()
  },
  methods: {
    async loadPoetry() {
      try {
        const data = await api.getPoetryList()
        this.poetryList = data || []
      } catch (e) {
        console.error(e)
      }
    },
    formatContent(content) {
      if (!content) return ''
      return content.replace(/\n/g, ' ')
    },
    goToDetail(id) {
      uni.navigateTo({ url: `/pages/poetry/detail?id=${id}` })
    }
  }
}
</script>

<style scoped>
.poetry-list-page {
  padding: 20rpx;
  background: #f8f8f8;
  min-height: 100vh;
}

.poetry-item {
  background: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
}

.poetry-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16rpx;
}

.poetry-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
}

.poetry-author {
  font-size: 24rpx;
  color: #999;
}

.poetry-content {
  font-size: 28rpx;
  color: #666;
  line-height: 1.8;
  display: block;
}

.poetry-footer {
  margin-top: 16rpx;
  padding-top: 16rpx;
  border-top: 1rpx solid #f0f0f0;
}

.scene-label {
  font-size: 24rpx;
  color: #4CAF50;
}

.scene-text {
  font-size: 24rpx;
  color: #999;
}

.empty {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
}
</style>

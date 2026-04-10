<template>
  <view class="poetry-detail-page" v-if="poetry">
    <view class="poetry-card">
      <text class="poetry-title">{{ poetry.title }}</text>
      <text class="poetry-author">{{ poetry.author }}</text>
      <view class="poetry-content">
        <text v-for="(line, index) in contentLines" :key="index" class="content-line">{{ line }}</text>
      </view>
    </view>

    <view class="scene-section" v-if="poetry.scene">
      <text class="section-title">创作背景</text>
      <text class="scene-text">{{ poetry.scene }}</text>
    </view>

    <view class="poetry-image" v-if="poetry.image">
      <image :src="poetry.image" mode="widthFix"></image>
    </view>
  </view>
</template>

<script>
import api from '../../api/index'

export default {
  data() {
    return {
      poetry: null,
      contentLines: []
    }
  },
  onLoad(options) {
    if (options.id) {
      this.loadDetail(options.id)
    }
  },
  methods: {
    async loadDetail(id) {
      try {
        const data = await api.getPoetryDetail(id)
        this.poetry = data
        if (data.content) {
          this.contentLines = data.content.split('\n')
        }
      } catch (e) {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>
.poetry-detail-page {
  padding: 20rpx;
  background: linear-gradient(180deg, #e8f5e9 0%, #f8f8f8 100%);
  min-height: 100vh;
}

.poetry-card {
  background: #fff;
  border-radius: 16rpx;
  padding: 40rpx;
  text-align: center;
}

.poetry-title {
  font-size: 40rpx;
  font-weight: bold;
  color: #333;
  display: block;
  margin-bottom: 16rpx;
}

.poetry-author {
  font-size: 28rpx;
  color: #999;
  display: block;
  margin-bottom: 32rpx;
}

.poetry-content {
  padding: 24rpx 0;
}

.content-line {
  display: block;
  font-size: 32rpx;
  color: #555;
  line-height: 2;
  letter-spacing: 4rpx;
}

.scene-section {
  background: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-top: 20rpx;
}

.section-title {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  display: block;
  margin-bottom: 16rpx;
}

.scene-text {
  font-size: 28rpx;
  color: #666;
  line-height: 1.6;
}

.poetry-image {
  margin-top: 20rpx;
  border-radius: 16rpx;
  overflow: hidden;
}

.poetry-image image {
  width: 100%;
}
</style>

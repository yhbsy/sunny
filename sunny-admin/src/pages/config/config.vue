<template>
  <view class="config-page">
    <view class="config-section">
      <view class="section-title">联系方式配置</view>
      
      <view class="form-item">
        <text class="form-label">微信号</text>
        <input class="form-input" v-model="config.wechat" placeholder="请输入微信号" />
        <view class="save-btn" @click="saveConfig('wechat')">保存</view>
      </view>

      <view class="form-item">
        <text class="form-label">抖音号</text>
        <input class="form-input" v-model="config.douyin" placeholder="请输入抖音号" />
        <view class="save-btn" @click="saveConfig('douyin')">保存</view>
      </view>

      <view class="form-item">
        <text class="form-label">联系电话</text>
        <input class="form-input" v-model="config.phone" placeholder="请输入联系电话" />
        <view class="save-btn" @click="saveConfig('phone')">保存</view>
      </view>

      <view class="form-item">
        <text class="form-label">地址</text>
        <input class="form-input" v-model="config.address" placeholder="请输入地址" />
        <view class="save-btn" @click="saveConfig('address')">保存</view>
      </view>
    </view>

    <view class="tips-section">
      <text class="tips-title">提示</text>
      <text class="tips-text">1. 微信号和抖音号将显示在前端首页和联系我们页面</text>
      <text class="tips-text">2. 联系电话可点击直接拨打</text>
      <text class="tips-text">3. 修改后前端将实时更新</text>
    </view>
  </view>
</template>

<script>
import api from '../../api/index'

export default {
  data() {
    return {
      config: {
        wechat: '',
        douyin: '',
        phone: '',
        address: ''
      }
    }
  },
  onLoad() {
    this.loadConfig()
  },
  methods: {
    async loadConfig() {
      try {
        const keys = ['wechat', 'douyin', 'phone', 'address']
        for (const key of keys) {
          const value = await api.getConfig(key)
          this.config[key] = value || ''
        }
      } catch (e) {
        console.error(e)
      }
    },
    async saveConfig(key) {
      try {
        await api.setConfig(key, this.config[key])
        uni.showToast({ title: '保存成功', icon: 'success' })
      } catch (e) {
        console.error(e)
      }
    }
  }
}
</script>

<style scoped>
.config-page {
  padding: 20rpx;
}

.config-section {
  background: #fff;
  border-radius: 12rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
}

.section-title {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 24rpx;
}

.form-item {
  margin-bottom: 32rpx;
}

.form-label {
  font-size: 28rpx;
  color: #333;
  margin-bottom: 12rpx;
  display: block;
}

.form-input {
  width: 100%;
  height: 80rpx;
  padding: 0 20rpx;
  border: 1rpx solid #ddd;
  border-radius: 8rpx;
  font-size: 28rpx;
  box-sizing: border-box;
  margin-bottom: 16rpx;
}

.save-btn {
  background: #1890ff;
  color: #fff;
  text-align: center;
  padding: 16rpx;
  border-radius: 8rpx;
  font-size: 28rpx;
}

.tips-section {
  background: #fffbe6;
  border-radius: 12rpx;
  padding: 24rpx;
  border: 1rpx solid #ffe58f;
}

.tips-title {
  font-size: 28rpx;
  font-weight: bold;
  color: #d48806;
  display: block;
  margin-bottom: 16rpx;
}

.tips-text {
  font-size: 26rpx;
  color: #d48806;
  display: block;
  line-height: 1.8;
}
</style>

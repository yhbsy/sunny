<template>
  <view class="poetry-edit-page">
    <view class="form-section">
      <view class="form-item">
        <text class="form-label">诗词标题 *</text>
        <input class="form-input" v-model="form.title" placeholder="请输入诗词标题" />
      </view>

      <view class="form-item">
        <text class="form-label">作者</text>
        <input class="form-input" v-model="form.author" placeholder="请输入作者" />
      </view>

      <view class="form-item">
        <text class="form-label">诗词内容 *</text>
        <textarea class="form-textarea" v-model="form.content" placeholder="请输入诗词内容，换行请使用回车"></textarea>
      </view>

      <view class="form-item">
        <text class="form-label">景物描述</text>
        <textarea class="form-textarea" v-model="form.scene" placeholder="请描述创作时的景物"></textarea>
      </view>

      <view class="form-item">
        <text class="form-label">配图</text>
        <view class="image-upload">
          <image v-if="form.image" class="preview-image" :src="form.image" mode="aspectFill"></image>
          <view class="upload-btn" @click="chooseImage">
            <text>+</text>
          </view>
        </view>
      </view>

      <view class="form-item">
        <text class="form-label">状态</text>
        <view class="status-switch">
          <view class="switch-item" :class="{ active: form.status === 1 }" @click="form.status = 1">显示</view>
          <view class="switch-item" :class="{ active: form.status === 0 }" @click="form.status = 0">隐藏</view>
        </view>
      </view>

      <view class="form-item">
        <text class="form-label">排序</text>
        <input class="form-input" v-model="form.sort" type="number" placeholder="数字越大越靠前" />
      </view>
    </view>

    <view class="form-actions">
      <view class="action-btn cancel" @click="goBack">取消</view>
      <view class="action-btn submit" @click="handleSubmit">保存</view>
    </view>
  </view>
</template>

<script>
import api from '../../api/index'

export default {
  data() {
    return {
      id: null,
      form: {
        title: '',
        author: '山里人',
        content: '',
        scene: '',
        image: '',
        status: 1,
        sort: 0
      }
    }
  },
  onLoad(options) {
    if (options.id) {
      this.id = parseInt(options.id)
      this.loadDetail()
    }
  },
  methods: {
    async loadDetail() {
      try {
        const data = await api.getPoetryDetail(this.id)
        this.form = { ...this.form, ...data }
      } catch (e) {
        console.error(e)
      }
    },
    chooseImage() {
      uni.chooseImage({
        count: 1,
        success: (res) => {
          this.form.image = res.tempFilePaths[0]
        }
      })
    },
    async handleSubmit() {
      if (!this.form.title) {
        uni.showToast({ title: '请输入诗词标题', icon: 'none' })
        return
      }
      if (!this.form.content) {
        uni.showToast({ title: '请输入诗词内容', icon: 'none' })
        return
      }

      try {
        if (this.id) {
          this.form.id = this.id
          await api.updatePoetry(this.form)
        } else {
          await api.addPoetry(this.form)
        }
        uni.showToast({ title: '保存成功', icon: 'success' })
        setTimeout(() => {
          uni.navigateBack()
        }, 1000)
      } catch (e) {
        console.error(e)
      }
    },
    goBack() {
      uni.navigateBack()
    }
  }
}
</script>

<style scoped>
.poetry-edit-page {
  padding: 20rpx;
  padding-bottom: 120rpx;
}

.form-section {
  background: #fff;
  border-radius: 12rpx;
  padding: 24rpx;
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
}

.form-textarea {
  width: 100%;
  padding: 20rpx;
  border: 1rpx solid #ddd;
  border-radius: 8rpx;
  font-size: 28rpx;
  box-sizing: border-box;
  min-height: 200rpx;
}

.image-upload {
  display: flex;
  align-items: center;
}

.preview-image {
  width: 160rpx;
  height: 160rpx;
  border-radius: 8rpx;
  margin-right: 20rpx;
}

.upload-btn {
  width: 160rpx;
  height: 160rpx;
  border: 2rpx dashed #ddd;
  border-radius: 8rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 60rpx;
  color: #ddd;
}

.status-switch {
  display: flex;
}

.switch-item {
  flex: 1;
  height: 72rpx;
  line-height: 72rpx;
  text-align: center;
  border: 1rpx solid #ddd;
  font-size: 28rpx;
  color: #999;
}

.switch-item:first-child {
  border-radius: 8rpx 0 0 8rpx;
}

.switch-item:last-child {
  border-radius: 0 8rpx 8rpx 0;
  border-left: none;
}

.switch-item.active {
  background: #722ed1;
  border-color: #722ed1;
  color: #fff;
}

.form-actions {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  padding: 20rpx;
  background: #fff;
  box-shadow: 0 -2rpx 10rpx rgba(0, 0, 0, 0.1);
}

.action-btn {
  flex: 1;
  height: 80rpx;
  line-height: 80rpx;
  text-align: center;
  border-radius: 8rpx;
  font-size: 32rpx;
}

.action-btn.cancel {
  background: #f5f5f5;
  color: #666;
  margin-right: 20rpx;
}

.action-btn.submit {
  background: #722ed1;
  color: #fff;
}
</style>

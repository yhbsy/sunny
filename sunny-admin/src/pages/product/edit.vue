<template>
  <view class="product-edit-page">
    <view class="form-section">
      <view class="form-item">
        <text class="form-label">商品名称 *</text>
        <input class="form-input" v-model="form.name" placeholder="请输入商品名称" />
      </view>

      <view class="form-item">
        <text class="form-label">商品分类 *</text>
        <picker :value="categoryIndex" :range="categoryList" range-key="name" @change="onCategoryChange">
          <view class="form-picker">
            {{ categoryList[categoryIndex]?.name || '请选择分类' }}
          </view>
        </picker>
      </view>

      <view class="form-item">
        <text class="form-label">商品价格 *</text>
        <input class="form-input" v-model="form.price" type="digit" placeholder="请输入价格" />
      </view>

      <view class="form-item">
        <text class="form-label">单位</text>
        <input class="form-input" v-model="form.unit" placeholder="请输入单位，如：斤、个" />
      </view>

      <view class="form-item">
        <text class="form-label">库存</text>
        <input class="form-input" v-model="form.stock" type="number" placeholder="请输入库存数量" />
      </view>

      <view class="form-item">
        <text class="form-label">产地</text>
        <input class="form-input" v-model="form.origin" placeholder="请输入产地" />
      </view>

      <view class="form-item">
        <text class="form-label">商品描述</text>
        <textarea class="form-textarea" v-model="form.description" placeholder="请输入商品描述"></textarea>
      </view>

      <view class="form-item">
        <text class="form-label">商品图片</text>
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
          <view class="switch-item" :class="{ active: form.status === 1 }" @click="form.status = 1">上架</view>
          <view class="switch-item" :class="{ active: form.status === 0 }" @click="form.status = 0">下架</view>
        </view>
      </view>

      <view class="form-item">
        <text class="form-label">排序</text>
        <input class="form-input" v-model="form.sort" type="number" placeholder="数字越小越靠前" />
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
        name: '',
        categoryId: null,
        price: '',
        unit: '斤',
        stock: 999,
        origin: '',
        description: '',
        image: '',
        status: 1,
        sort: 0
      },
      categoryList: [],
      categoryIndex: 0
    }
  },
  onLoad(options) {
    if (options.id) {
      this.id = parseInt(options.id)
      this.loadDetail()
    }
    this.loadCategories()
  },
  methods: {
    async loadCategories() {
      try {
        const data = await api.getCategoryList()
        this.categoryList = data || []
        if (this.form.categoryId) {
          const index = this.categoryList.findIndex(c => c.id === this.form.categoryId)
          if (index > -1) {
            this.categoryIndex = index
          }
        }
      } catch (e) {
        console.error(e)
      }
    },
    async loadDetail() {
      try {
        const data = await api.getProductDetail(this.id)
        this.form = { ...this.form, ...data }
      } catch (e) {
        console.error(e)
      }
    },
    onCategoryChange(e) {
      this.categoryIndex = e.detail.value
      this.form.categoryId = this.categoryList[this.categoryIndex].id
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
      if (!this.form.name) {
        uni.showToast({ title: '请输入商品名称', icon: 'none' })
        return
      }
      if (!this.form.categoryId) {
        uni.showToast({ title: '请选择商品分类', icon: 'none' })
        return
      }
      if (!this.form.price) {
        uni.showToast({ title: '请输入商品价格', icon: 'none' })
        return
      }

      try {
        if (this.id) {
          this.form.id = this.id
          await api.updateProduct(this.form)
        } else {
          await api.addProduct(this.form)
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
.product-edit-page {
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

.form-picker {
  width: 100%;
  height: 80rpx;
  padding: 0 20rpx;
  border: 1rpx solid #ddd;
  border-radius: 8rpx;
  font-size: 28rpx;
  line-height: 80rpx;
  box-sizing: border-box;
  color: #333;
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
  background: #1890ff;
  border-color: #1890ff;
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
  background: #1890ff;
  color: #fff;
}
</style>

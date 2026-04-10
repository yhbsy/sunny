<template>
  <view class="poetry-list-page">
    <view class="search-bar">
      <input class="search-input" v-model="keyword" placeholder="搜索诗词" />
      <view class="search-btn" @click="search">搜索</view>
      <view class="add-btn" @click="goToAdd">添加</view>
    </view>

    <view class="poetry-list">
      <view class="poetry-item" v-for="item in poetryList" :key="item.id">
        <view class="poetry-info">
          <text class="poetry-title">{{ item.title }}</text>
          <text class="poetry-author">{{ item.author }}</text>
          <text class="poetry-content">{{ formatContent(item.content) }}</text>
        </view>
        <view class="poetry-actions">
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
      poetryList: [],
      keyword: '',
      pageNum: 1,
      pageSize: 10,
      total: 0,
      totalPages: 1
    }
  },
  onLoad() {
    this.loadPoetry()
  },
  methods: {
    async loadPoetry() {
      try {
        const data = await api.getPoetryPage({
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          keyword: this.keyword
        })
        this.poetryList = data?.records || []
        this.total = data?.total || 0
        this.totalPages = Math.ceil(this.total / this.pageSize) || 1
      } catch (e) {
        console.error(e)
      }
    },
    formatContent(content) {
      if (!content) return ''
      return content.replace(/\n/g, ' ').substring(0, 50) + '...'
    },
    search() {
      this.pageNum = 1
      this.loadPoetry()
    },
    prevPage() {
      if (this.pageNum > 1) {
        this.pageNum--
        this.loadPoetry()
      }
    },
    nextPage() {
      if (this.pageNum < this.totalPages) {
        this.pageNum++
        this.loadPoetry()
      }
    },
    goToAdd() {
      uni.navigateTo({ url: '/pages/poetry/edit' })
    },
    goToEdit(id) {
      uni.navigateTo({ url: `/pages/poetry/edit?id=${id}` })
    },
    handleDelete(id) {
      uni.showModal({
        title: '提示',
        content: '确定删除该诗词吗?',
        success: async (res) => {
          if (res.confirm) {
            try {
              await api.deletePoetry(id)
              uni.showToast({ title: '删除成功', icon: 'success' })
              this.loadPoetry()
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
.poetry-list-page {
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
  background: #722ed1;
  color: #fff;
}

.poetry-list {
  background: #fff;
  border-radius: 12rpx;
}

.poetry-item {
  display: flex;
  padding: 20rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.poetry-info {
  flex: 1;
}

.poetry-title {
  font-size: 30rpx;
  font-weight: bold;
  color: #333;
  display: block;
  margin-bottom: 8rpx;
}

.poetry-author {
  font-size: 24rpx;
  color: #999;
  display: block;
  margin-bottom: 8rpx;
}

.poetry-content {
  font-size: 26rpx;
  color: #666;
  line-height: 1.5;
}

.poetry-actions {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.action-btn {
  padding: 12rpx 24rpx;
  border-radius: 4rpx;
  font-size: 24rpx;
  margin-bottom: 8rpx;
}

.action-btn.edit {
  background: #f9f0ff;
  color: #722ed1;
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

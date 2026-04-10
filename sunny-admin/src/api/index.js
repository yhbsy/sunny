import request from '../utils/request'

export default {
  login(data) {
    return request.post('/admin/login', data)
  },

  getAdminInfo(username) {
    return request.get('/admin/info', { username })
  },

  getCategoryList() {
    return request.get('/category/list')
  },

  getProductPage(params) {
    return request.get('/product/page', params)
  },

  getProductDetail(id) {
    return request.get(`/product/${id}`)
  },

  addProduct(data) {
    return request.post('/product', data)
  },

  updateProduct(data) {
    return request.put('/product', data)
  },

  deleteProduct(id) {
    return request.delete(`/product/${id}`)
  },

  getPoetryPage(params) {
    return request.get('/poetry/page', params)
  },

  getPoetryDetail(id) {
    return request.get(`/poetry/${id}`)
  },

  addPoetry(data) {
    return request.post('/poetry', data)
  },

  updatePoetry(data) {
    return request.put('/poetry', data)
  },

  deletePoetry(id) {
    return request.delete(`/poetry/${id}`)
  },

  getOrderPage(params) {
    return request.get('/order/list', params)
  },

  getConfig(key) {
    return request.get(`/config/${key}`)
  },

  setConfig(key, value) {
    return request.post('/config', null, { params: { key, value } })
  }
}

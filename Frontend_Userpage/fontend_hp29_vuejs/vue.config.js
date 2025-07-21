const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 8081, // Thay đổi số cổng tại đây
  }

})

// const { defineConfig } = require('@vue/cli-service');
const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = defineConfig({
  devServer: {
    proxy: {
      '/api': {
        target: 'https://sellingpartnerapi-na.amazon.com',
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
      },
    },
  },
});
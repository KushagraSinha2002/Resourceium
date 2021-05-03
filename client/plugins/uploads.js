export default ({ app }, inject) => {
  inject('uploadFile', (userId, formData) => {
    return app.$axios
      .post(`/uploads/upload/${userId}`, formData)
      .then((response) => {
        app.$addAlert({
          message: response.data.message,
          type: 'success',
        })
      })
  })
}

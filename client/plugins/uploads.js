export default ({ app }, inject) => {
  inject('uploadFile', (userId, formData) => {
    return app.$axios
      .post(`/uploads/upload/${userId}`, formData)
      .then((response) => {
        app.$addAlert({
          severity: 'success',
          messageHeading: 'Upload successful',
          messageBody: response.data.message,
          active: true,
        })
      })
  })
}

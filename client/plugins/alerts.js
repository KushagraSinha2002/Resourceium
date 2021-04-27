export default ({ store }, inject) => {
  // this will set add an alert item to the vuex store which will be displayed
  // according to whether it is active or not
  inject('addAlert', (alert) => {
    store.dispatch('alerts/addAlert', alert)
  })
  inject('disableAlert', (alertId) => {
    store.dispatch('alerts/disableAlert', alertId)
  })
}

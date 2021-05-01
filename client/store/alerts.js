export const state = () => ({
  message: null,
  timeOut: 2000,
  show: false,
  type: 'info',
})

export const mutations = {
  addAlert(state, payload) {
    state.message = payload.message
    payload.timeOut !== undefined
      ? (state.timeOut = payload.timeOut)
      : (state.timeOut = 2000)
    state.show = true
    payload.type !== undefined
      ? (state.type = payload.type)
      : (state.type = 'info')
  },
  disableAlert(state) {
    state.message = null
    state.timeOut = null
    state.show = false
  },
}

export const actions = {
  addAlert({ commit }, payload) {
    commit('addAlert', payload)
  },
  disableAlert({ commit }) {
    commit('disableAlert')
  },
}

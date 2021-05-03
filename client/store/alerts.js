export const state = () => ({
  message: null,
  timeOut: 2000,
  type: 'info',
})

export const mutations = {
  addAlert(state, payload) {
    state.message = payload.message
    payload.timeOut !== undefined
      ? (state.timeOut = payload.timeOut)
      : (state.timeOut = 2000)
    payload.type !== undefined
      ? (state.type = payload.type)
      : (state.type = 'info')
  },
  disableAlert(state) {
    state.message = null
    state.timeOut = null
  },
}

export const actions = {
  addAlert({ commit }, payload) {
    commit('blur/enableBlur', {}, { root: true })
    commit('addAlert', payload)
  },
  disableAlert({ commit }) {
    commit('blur/disableBlur', {}, { root: true })
    commit('disableAlert')
  },
}

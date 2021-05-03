export const state = () => ({
  show: false,
})

export const mutations = {
  enableBlur(state) {
    state.show = true
  },
  disableBlur(state) {
    state.show = false
  },
}

export const state = () => ({
  show: false,
})

export const mutations = {
  toggleSidebar(state) {
    state.show = !state.show
  },
}

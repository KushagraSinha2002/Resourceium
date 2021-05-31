<template>
  <div>
    <page-dashboard-graph-one></page-dashboard-graph-one>
  </div>
</template>

<script>
export default {
  middleware: ['auth'],
  async fetch() {
    // const graphTwo = await this.$axios.$get('/core/dashboard/graph/2')
    // const graphThree = await this.$axios.$get('/core/dashboard/graph/3')
    // const graphFour = await this.$axios.$get('/core/dashboard/graph/4')
    // console.log(graphOne, graphTwo, graphThree, graphFour)
  },
}
</script>

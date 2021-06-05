<template>
  <div class="lg:px-3 xl:px-10 2xl:px-32">
    <page-dashboard-stats class="my-5"></page-dashboard-stats>
    <page-dashboard-graph-one></page-dashboard-graph-one>
  </div>
</template>

<script>
export default {
  middleware: ['auth'],
}
</script>

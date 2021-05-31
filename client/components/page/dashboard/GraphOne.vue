<template>
  <div v-if="graphData">
    <page-dashboard-line
      :data="graphData"
      :options="options"
    ></page-dashboard-line>
  </div>
</template>

<script>
export default {
  data() {
    return {
      graphData: null,
      options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: { display: false },
        title: {
          display: true,
          text: `Files uploaded by ${this.$auth.user.username}`,
          fontSize: 24,
          fontColor: '#6b7280',
        },
        tooltips: {
          backgroundColor: '#17BF62',
        },
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: false,
                steps: 10,
                stepValue: 5,
                max: 100,
              },
            },
          ],
        },
      },
    }
  },
  async fetch() {
    const response = await this.$axios.$get('/core/dashboard/graph/1')
    this.graphData = {
      labels: Object.keys(response).map((x) =>
        this.$dayjs(x).format('DD MMM, YYYY')
      ),
      datasets: [{ data: Object.values(response) }],
    }
  },
}
</script>

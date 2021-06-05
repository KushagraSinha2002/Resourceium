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
      maxFiles: 0,
      options: {
        responsive: true,
        maintainAspectRatio: false,
        legend: { display: false },
        title: {
          display: true,
          text: `Files uploaded by ${this.$auth.user.username}`,
          fontSize: 24,
          fontColor: '#120201',
          fontFamily: 'Oxygen Mono',
        },
        tooltips: {
          backgroundColor: '#7527a8',
        },
        scales: {
          xAxes: [
            {
              ticks: {
                autoSkip: false,
                maxRotation: 70,
                minRotation: 70,
                fontSize: 20,
              },
            },
          ],
          yAxes: [
            {
              ticks: {
                beginAtZero: false,
                max: this.maxFiles,
                fontSize: 15,
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
      labels: Object.keys(response).map((x) => this.$dayjs(x).format('DD-MMM')),
      datasets: [
        {
          data: Object.values(response),
          backgroundColor: 'rgba(169, 209, 232, 0.4)',
          borderCapStyle: 'round',
          borderJoinStyle: 'round',
          label: 'Files uploaded',
        },
      ],
    }
    this.maxFiles = Math.max.apply(Math, Object.values(response))
  },
}
</script>

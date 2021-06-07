<template>
  <div v-if="graphData">
    <div
      class="font-serif text-2xl font-semibold text-center sm:text-3xl lg:text-4xl"
    >
      Files uploaded for the past 7 days
    </div>
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
          display: false,
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
          backgroundColor: 'rgba(150, 56, 220, 0.37)',
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

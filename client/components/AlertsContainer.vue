<template>
  <div class="absolute bottom-5 right-3 sm:right-10 md:right-14">
    <div
      class="relative"
      :class="{ 'animate-bounce': activeAlertsGetter.length != 0 && !show }"
    >
      <ig-icon
        name="bell"
        no-size
        no-color
        class="z-20 w-8 h-8 cursor-pointer sm:h-10 sm:w-10 md:h-14 md:w-14"
        @click.native="show = !show"
      ></ig-icon>
      <div
        v-show="activeAlertsGetter.length != 0"
        class="absolute w-3 h-3 bg-red-600 rounded-full -right-1 md:right-0 -top-1 md:top-0 ring-1 ring-black"
      ></div>
      <div
        v-show="show && activeAlertsGetter.length != 0"
        class="absolute px-3 py-2 bg-gray-200 bg-opacity-75 border border-black rounded-lg bottom-8 right-8 w-72 sm:bottom-10 sm:right-10 md:bottom-14 md:right-14"
      >
        <div
          v-for="alert in activeAlertsGetter"
          v-show="alert.active"
          :id="`alert-${alert.id}`"
          :key="alert.id"
          class="w-full my-3"
        >
          <div
            class="flex justify-between p-3 space-x-3 border-4 rounded-lg shadow-xl bg-opacity-20"
            :class="{
              'border-green-400': alert.severity === 'success',
              'border-blue-400': alert.severity === 'info',
              'border-yellow-400': alert.severity === 'warning',
              'border-red-500': alert.severity === 'danger',
            }"
          >
            <div class="block space-y">
              <div class="block text-lg font-semibold underline">
                {{ alert.messageHeading }}
              </div>
              <div v-if="alert.messageBody" class="block text-sm">
                {{ alert.messageBody }}
              </div>
            </div>
            <icons-time
              class="cursor-pointer"
              @click.native="closeAlert(alert.id)"
            ></icons-time>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from 'vuex'

export default {
  data: () => ({
    show: false,
  }),
  computed: {
    ...mapState({
      alerts: (state) => state.alerts.alerts,
    }),
    ...mapGetters({
      activeAlertsGetter: 'alerts/activeAlerts',
    }),
  },
  methods: {
    ...mapActions({
      disableAlertAction: 'alerts/disableAlert',
    }),
    closeAlert(id) {
      this.disableAlertAction(id)
    },
  },
}
</script>

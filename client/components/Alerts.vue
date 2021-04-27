<template>
  <!-- Giving the div a fixed width prevents the last alert from shrinking when we dismiss it.  -->
  <!-- Remove the `max-w-[18rem]` class, and trigger an alert, and dismiss it, to see what I mean. -->
  <div id="alert--area" class="fixed z-10 max-w-[18rem]">
    <transition-group name="alert" tag="div">
      <div
        v-for="alert in activeAlertsGetter"
        :id="`alert-${alert.id}`"
        :key="alert.id"
        class="w-full my-3"
      >
        <div
          v-show="alert.active"
          class="flex p-3 space-x-3 shadow-xl rounded-r-xl"
          :class="{
            'bg-green-400': alert.severity === 'success',
            'bg-blue-400': alert.severity === 'info',
            'bg-yellow-400': alert.severity === 'warning',
            'bg-red-500': alert.severity === 'danger',
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
    </transition-group>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from 'vuex'

export default {
  data: () => ({
    closed: false,
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

<style lang="scss" scoped>
.alert-leave-active,
.alert-enter-active {
  transform: translateX(-70px);
  transition-duration: 1s;
  transition-property: opacity, transform, background-color;
}

.alert-leave-active {
  position: absolute;
}

.alert-leave-to {
  opacity: 0;
}

.alert-enter-to {
  transform: translateX(0);
}

.alert-move {
  transition: transform 1s;
}
</style>

<template>
  <div class="grid grid-cols-1 sm:grid-cols-3">
    <div class="text-gray-200 uppercase sm:col-span-1 md:text-lg font-poppins">
      {{ unslugify(label) }}
    </div>
    <div
      class="flex items-center justify-between pb-1 space-x-3 border-b sm:col-span-2"
    >
      <div class="truncate md:text-lg font-poppins text-warm-gray-200">
        {{ identifier }}
      </div>
      <PenToolIcon
        v-if="determineEditable"
        class="text-blue-500 cursor-pointer"
        @click.native="changeData()"
      ></PenToolIcon>
    </div>
  </div>
</template>

<script>
import { PenToolIcon } from 'vue-feather-icons'

export default {
  components: { PenToolIcon },
  props: {
    identifier: {
      type: [String, Number],
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
    editable: {
      type: Boolean,
      default: false,
    },
  },
  computed: {
    determineEditable() {
      return [
        'firstName',
        'lastName',
        'bio',
        'password',
        'country',
        'dateOfBirth',
      ].includes(this.label)
    },
  },
  methods: {
    changeData() {
      const newData = prompt(
        `Enter new ${this.unslugify(this.label)}`,
        this.identifier
      )
      if (newData !== null) {
        this.$emit('changeData', this.label, newData)
      }
    },
    unslugify(val) {
      const result = val.replace(/([A-Z])/g, ' $1')
      return result.charAt(0).toUpperCase() + result.slice(1)
    },
  },
}
</script>

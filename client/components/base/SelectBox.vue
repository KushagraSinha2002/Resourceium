<template>
  <div>
    <label
      class="text-sm font-bold tracking-wider uppercase font-poppins"
      :for="name"
    >
      {{ label }}
    </label>
    <select
      :id="name"
      :name="name"
      class="w-full px-4 py-2 transition bg-transparent border hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
      @input="handleInput($event.target.value)"
    >
      <option value="" selected disabled hidden>Select an Option</option>
      <option
        v-for="(option, index) in selectOptions"
        :key="index"
        :value="option"
      >
        {{ option }}
      </option>
    </select>
  </div>
</template>

<script>
export default {
  props: {
    name: {
      type: String,
      required: true,
    },
    value: {
      type: String,
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
    selectOptions: {
      type: Array,
      required: true,
    },
  },
  methods: {
    handleInput(val) {
      this.$emit('input', val)
    },
  },
}
</script>

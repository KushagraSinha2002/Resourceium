<template>
  <vue-autosuggest
    v-model="query"
    :suggestions="filteredOptions"
    :get-suggestion-value="getSuggestionValue"
    :input-props="{
      id: 'autosuggest-input',
      placeholder: 'Type username',
    }"
    component-attr-class-autosuggest-results="results-container"
    @input="onInputChange"
    @selected="onSelected"
  >
    <div slot-scope="{ suggestion }">
      {{ suggestion.item.username }}
    </div>
  </vue-autosuggest>
</template>

<script>
import { VueAutosuggest } from 'vue-autosuggest'
import { withQuery } from 'ufo'

export default {
  components: {
    VueAutosuggest,
  },
  data() {
    return {
      query: '',
      suggestions: [
        {
          data: [],
        },
      ],
    }
  },
  computed: {
    filteredOptions() {
      return [
        {
          data: this.suggestions[0].data,
        },
      ]
    },
    getUsernamesURL() {
      return withQuery('/accounts/get-by-username', { username: this.query })
    },
  },
  methods: {
    onSelected(item) {
      if (item) {
        this.selected = item.item.id
        this.$emit('selected', item.item)
      }
    },
    onInputChange(_text) {
      // event fired when the input changes
      this.getUsernames()
    },
    getSuggestionValue(suggestion) {
      return suggestion.item.username
    },
    async getUsernames() {
      const resp = await this.$axios.$get(this.getUsernamesURL)
      this.suggestions[0].data = resp
    },
  },
}
</script>

<style>
/* stylelint-disable */
#autosuggest {
  @apply relative;
}

#autosuggest-input,
#autosuggest {
  @apply w-full;
}

#autosuggest-input {
  @apply px-4 py-2 transition bg-transparent border appearance-none resize-none hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300;
}

.results-container {
  @apply inset-x-4 absolute z-10 bg-blue-500 rounded-b-15px bg-opacity-80 p-1.5 lg:p-3;
}
</style>

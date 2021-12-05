<template>
  <div>
    <!-- Image upload component assist -->
    <el-upload
      class="avatar-uploader quill-img"
      :action="uploadImgUrl"
      name="file"
      :headers="headers"
      :show-file-list="false"
      :on-success="quillImgSuccess"
      :on-error="uploadError"
      :before-upload="quillImgBefore"
      accept='.jpg,.jpeg,.png,.gif'
    ></el-upload>

    <!-- Rich text component -->
    <quill-editor
      class="editor"
      v-model="content"
      ref="quillEditor"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @change="onEditorChange($event)"
    ></quill-editor>
  </div>
</template>

<script>
import { getToken } from '@/utils/auth'

// Toolbar Configuration
const toolbarOptions = [
  ["bold", "italic", "underline", "strike"],       // bold italics The underline Delete the line
  ["blockquote", "code-block"],                    // reference  The code block
  [{ list: "ordered" }, { list: "bullet" }],       // The orderly、Unordered list
  [{ indent: "-1" }, { indent: "+1" }],            // The indentation
  [{ size: ["small", false, "large", "huge"] }],   // The font size
  [{ header: [1, 2, 3, 4, 5, 6, false] }],         // The title
  [{ color: [] }, { background: [] }],             // The font color、Font background color
  [{ align: [] }],                                 // alignment
  ["clean"],                                       // Clear text formatting
  ["link", "image", "video"]                       // link、The picture、video
];

import { quillEditor } from "vue-quill-editor";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";

export default {
  props: {
    /* Contents of the editor */
    value: {
      type: String
    },
    /* Image size */
    maxSize: {
      type: Number,
      default: 4000 //kb
    }
  },
  components: { quillEditor },
  data() {
    return {
      content: this.value,
      uploadImgUrl: "",
      editorOption: {
        placeholder: "",
        theme: "snow", // or 'bubble'
        placeholder: "Please enter the content",
        modules: {
          toolbar: {
            container: toolbarOptions,
            handlers: {
              image: function(value) {
                if (value) {
                  // The triggerinputBox to select the picture file
                  document.querySelector(".quill-img input").click();
                } else {
                  this.quill.format("image", false);
                }
              }
            }
          }
        }
      },
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload", // Address of the uploaded image server
      headers: {
        Authorization: 'Bearer ' + getToken()
      }
    };
  },
  watch: {
    value: function() {
      this.content = this.value;
    }
  },
  methods: {
    onEditorBlur() {
      //Out-of-focus event
    },
    onEditorFocus() {
      //Get focus event
    },
    onEditorChange() {
      //Content change event
      this.$emit("input", this.content);
    },

    // Rich text images before uploading
    quillImgBefore(file) {
      let fileType = file.type;
			if(fileType === 'image/jpeg' || fileType === 'image/png'){
				return true;
			}else {
				this.$message.error('Please insert the image type file(jpg/jpeg/png)');
				return false;
			}
    },

    quillImgSuccess(res, file) {
      // resData returned for the image server
      // Gets a rich text component instance
      let quill = this.$refs.quillEditor.quill;
      // If the upload succeeds
      if (res.code == 200) {
        // Gets the cursor position
        let length = quill.getSelection().index;
        // Insert the picture  res.urlThe address of the picture returned by the server
        quill.insertEmbed(length, "image", res.url);
        // Adjust the cursor to the end
        quill.setSelection(length + 1);
      } else {
        this.$message.error("Image insertion failed");
      }
    },
    // Failed to upload rich text image
    uploadError() {
      // loadingAnimation disappear
      this.$message.error("Image insertion failed");
    }
  }
};
</script> 

<style>
.editor {
  line-height: normal !important;
  height: 192px;
}
.quill-img {
  display: none;
}
.ql-snow .ql-tooltip[data-mode="link"]::before {
  content: "Please enter the link address:";
}
.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
  border-right: 0px;
  content: "save";
  padding-right: 0px;
}

.ql-snow .ql-tooltip[data-mode="video"]::before {
  content: "Please enter the video address:";
}

.ql-snow .ql-picker.ql-size .ql-picker-label::before,
.ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: "14px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
  content: "10px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
  content: "18px";
}
.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
  content: "32px";
}

.ql-snow .ql-picker.ql-header .ql-picker-label::before,
.ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: "The text";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
  content: "The title1";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
  content: "The title2";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
  content: "The title3";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
  content: "The title4";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
  content: "The title5";
}
.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
  content: "The title6";
}

.ql-snow .ql-picker.ql-font .ql-picker-label::before,
.ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: "Standard font";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
  content: "Serif fonts";
}
.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
  content: "Monospaced font";
}
</style>
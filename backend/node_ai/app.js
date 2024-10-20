import express from "express";
import fs from "fs";
import path from "path";
import { GoogleGenerativeAI } from "@google/generative-ai";
import { GoogleAIFileManager } from "@google/generative-ai/server";

const app = express();
const API_KEY = "AIzaSyAP5HdTGyDI9q0-3OsKqQ8XwicT4-_rZ_o";

const mimeTypes = {
    '.jpg': 'image/jpeg',
    '.jpeg': 'image/jpeg',
    '.png': 'image/png',
    '.gif': 'image/gif',
    '.bmp': 'image/bmp',
    '.webp': 'image/webp',
    // Add more types as needed
};

function getMimeType(filePath) {
    const ext = path.extname(filePath).toLowerCase();
    return mimeTypes[ext] || 'application/octet-stream'; // Fallback to octet-stream if unknown
}

async function run(filePath) {
    const fileManager = new GoogleAIFileManager(API_KEY);

    try {
        const mimeType = getMimeType(filePath); // Get MIME type based on extension
        const displayName = path.basename(filePath); // Extracting file name as displayName

        const uploadResult = await fileManager.uploadFile(filePath, {
            mimeType: mimeType,
            displayName: displayName,
        });

        const genAI = new GoogleGenerativeAI(API_KEY);
        const model = genAI.getGenerativeModel({ model: "gemini-1.5-flash" });

        const result = await model.generateContent([
            // "Could you please provide a suitable name, description, and tags for this image? Please use the following format: name | description | tag1, tag2, etc.",
            "You are a digital marketing expert. Please write name , description , tags. Please make sure they are SEO optimized and provide it in this following format.: name | description | tag1, tag2, etc.Please make sure it is simple text without any text transformations.",
            {
                fileData: {
                    fileUri: uploadResult.file.uri,
                    mimeType: uploadResult.file.mimeType,
                },
            },
        ]);

        return result.response.text();
    } catch (error) {
        console.error("Error generating content:", error);
        throw new Error("Failed to generate content");
    }
}

app.get("/generate-content/:userId/:fileName", async (req, res) => {
    const {userId , fileName} = req.params;
    const filePath = `C:\\Users\\Abhinav\\Desktop\\VS Code Projects\\Projects\\UrbanVoyage\\FS_UV\\${userId}\\${fileName}`;

    try {
        const response = await run(filePath);
        res.json({ response });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
});

// Start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
